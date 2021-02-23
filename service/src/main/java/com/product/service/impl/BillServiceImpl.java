package com.product.service.impl;

import com.product.dao.repository.CheckAccountLineRepository;
import com.product.dao.repository.CheckAccountRepository;
import com.product.dao.repository.OrderLineRepository;
import com.product.dao.repository.OrderRepository;
import com.product.dao.repository.unit.SpecificationUnit;
import com.product.entity.CheckAccount;
import com.product.entity.CheckAccountLine;
import com.product.entity.Order;
import com.product.entity.OrderLine;
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.BillService;
import com.product.service.unit.CommonUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BillServiceImpl implements BillService {

    private final static Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    CheckAccountRepository checkAccountRepository;

    @Autowired
    CheckAccountLineRepository checkAccountLineRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderLineRepository orderLineRepository;

    @Override
    public Page<CheckAccount> findList(CheckAccount checkAccount, PageRequest pageRequest) {

        SpecificationUnit<CheckAccount> specificationUnit = new SpecificationUnit(checkAccount);

        return checkAccountRepository.findAll(specificationUnit,pageRequest);
    }

    @Override
    public ResultVO findOne(Integer id ) {

        Optional<CheckAccount> checkAccountOptional = checkAccountRepository.findById(id);
        if (!checkAccountOptional.isPresent()){
            return ResultVOUtil.fail(ResultEnum.NOT_FIND_RECODE);
        }

        return ResultVOUtil.success(checkAccountOptional.get());
    }

    public Page<CheckAccountLine> findLine(Integer id,PageRequest pageRequest){

        Optional<CheckAccount> checkAccountOptional = checkAccountRepository.findById(id);
        if(!checkAccountOptional.isPresent()){
            return null;
        }

        return checkAccountLineRepository.findByCheckIdOrderByCheckLineIdAsc(id,pageRequest);

    }

    @Override
    @Transactional
    public ResultVO create(String ym) {

        if(ym.length() != 6 || !CommonUtil.isNumeric(ym)){
            log.info("创建对账单失败");
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"传入6位长度年月，如：202102");
        }

        String year = ym.substring(0,4);
        String mm = ym.substring(4,6);

        String checkCode = checkCodeGenerator(ym);

        CheckAccount checkAccount = new CheckAccount();
        checkAccount.setCheckCode(checkCode);
        checkAccount.setYears(year);
        checkAccount.setMonths(mm);
        checkAccount.setStatus("制单");

        Integer y = new Integer(year);
        Integer m = new Integer(mm);

        Date start = CommonUtil.getMonthFirstDay(y,m);
        Date end = CommonUtil.getMonthLastDay(y,m);

        checkAccount.setOperator(1);
        checkAccount = checkAccountRepository.save(checkAccount);

        Integer checkId = checkAccount.getCheckId();

        List<Order> shList = orderRepository.findByFinishTimeBetweenAndOrderTypeOrderByOrderId(start,end,"送货单");
        List<CheckAccountLine> checkAccountLines = new ArrayList<>();
        BigDecimal sumPrice = new BigDecimal("0.0");
        CheckAccountLine checkAccountLine;

        for(Order order : shList){
            List<OrderLine> orderLines = orderLineRepository.findByOrderIdOrderByOrderLineId(order.getOrderId());
            int row  = 1;

            for (OrderLine orderLine : orderLines){
                checkAccountLine = setCheckAccountLine(order,row,checkId,orderLine);
                checkAccountLines.add(checkAccountLine);
                sumPrice = sumPrice.add(checkAccountLine.getSumPrice());
                row++;
            }
        }
        List<Order> thList = orderRepository.findByFinishTimeBetweenAndOrderTypeOrderByOrderId(start,end,"退货单");
        for(Order order : thList){
            List<OrderLine> orderLines = orderLineRepository.findByOrderIdOrderByOrderLineId(order.getOrderId());
            int row  = 1;

            for (OrderLine orderLine : orderLines){
                checkAccountLine = setCheckAccountLine(order,row,checkId,orderLine);

                checkAccountLines.add(checkAccountLine);
                sumPrice = sumPrice.subtract(checkAccountLine.getSumPrice());
                row++;
            }
        }

        checkAccountLineRepository.saveAll(checkAccountLines);
        checkAccount.setSumPrice(sumPrice);
        checkAccountRepository.save(checkAccount);

        return ResultVOUtil.success(checkAccount.getCheckId());
    }

    @Override
    @Transactional
    public ResultVO publish(Integer id) {
        ResultEnum resultEnum = validStatus(id);

        if (!resultEnum.equals(ResultEnum.SUCCESS)){
            log.info("发布对账单失败");
            return ResultVOUtil.fail(resultEnum);
        }

        if (checkAccountLineRepository.countByCheckId(id)<=0){
            log.info("发布对账单失败");
            return ResultVOUtil.fail(ResultEnum.BILL_PUBLISH_ERROR);
        }
        CheckAccount checkAccount = checkAccountRepository.findById(id).get();
        checkAccount.setCheckId(id);
        checkAccount.setStatus("已发布");
        checkAccountRepository.save(checkAccount);


        return ResultVOUtil.success();
    }

    @Override
    @Transactional
    public ResultVO delete(Integer id) {

        ResultEnum resultEnum = validStatus(id);

        if (!resultEnum.equals(ResultEnum.SUCCESS)){
            log.info("删除对账单失败");
            return ResultVOUtil.fail(resultEnum);
        }
        checkAccountLineRepository.deleteByCheckId(id);
        checkAccountRepository.deleteById(id);

        return ResultVOUtil.success();
    }

    @Override
    public String toExcel(OutputStream outputStream, Integer id) {

        if(!checkAccountRepository.existsById(id)){
            log.info("导出对账单失败");
            return ResultEnum.NOT_FIND_RECODE.getMsg();
        }
        CheckAccount checkAccount = checkAccountRepository.findById(id).get();

        String[] strings = {"订单编号","行号","单据类型","客户编码","客户名称","产品编码","产品名称","单位","数量","单价","总价","完结日期"};

        SXSSFWorkbook workbook = new SXSSFWorkbook();

        SXSSFSheet sheet = workbook.createSheet(checkAccount.getCheckCode());

        SXSSFRow sxssfRow = sheet.createRow(0);
        CellStyle headCellStyle = workbook.createCellStyle();
        headCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font headCellFont = workbook.createFont();
        headCellFont.setBold(true);
        headCellStyle.setFont(headCellFont);
        headCellStyle.setBorderBottom(BorderStyle.THIN);
        headCellStyle.setBorderLeft(BorderStyle.THIN);
        headCellStyle.setBorderRight(BorderStyle.THIN);
        headCellStyle.setBorderTop(BorderStyle.THIN);

        SXSSFCell sxssfCell;

        for(int i =0 ; i< strings.length ; i++){
            sxssfCell = sxssfRow.createCell(i);
            sxssfCell.setCellValue(strings[i]);
            sxssfCell.setCellStyle(headCellStyle);
        }

        CellStyle lineCellStyle = workbook.createCellStyle();
        lineCellStyle.setBorderBottom(BorderStyle.THIN);
        lineCellStyle.setBorderLeft(BorderStyle.THIN);
        lineCellStyle.setBorderRight(BorderStyle.THIN);
        lineCellStyle.setBorderTop(BorderStyle.THIN);

        List<CheckAccountLine> checkAccountLines = checkAccountLineRepository.findByCheckIdOrderByCheckLineIdAsc(id);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int i =0;

        for (CheckAccountLine checkAccountLine : checkAccountLines){

            sxssfRow = sheet.createRow(i+1);

            sxssfRow.createCell(0).setCellValue(checkAccountLine.getOrderCode());
            sxssfRow.createCell(1).setCellValue(checkAccountLine.getRowNum());
            sxssfRow.createCell(2).setCellValue(checkAccountLine.getOrderType());
            sxssfRow.createCell(3).setCellValue(checkAccountLine.getCustomerCode());
            sxssfRow.createCell(4).setCellValue(checkAccountLine.getCustomerName());
            sxssfRow.createCell(5).setCellValue(checkAccountLine.getProductCode());
            sxssfRow.createCell(6).setCellValue(checkAccountLine.getProductName());
            sxssfRow.createCell(7).setCellValue(checkAccountLine.getUnit());
            sxssfRow.createCell(8).setCellValue(checkAccountLine.getActualQuantity());
            sxssfRow.createCell(9).setCellValue(checkAccountLine.getFinishPrice().doubleValue());
            sxssfRow.createCell(10).setCellValue(checkAccountLine.getSumPrice().doubleValue());
            sxssfRow.createCell(11).setCellValue(simpleDateFormat.format(checkAccountLine.getFinishDate()));

            i++;
        }

        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return ResultEnum.EXPORT_EXCEL_ERROR.getMsg();
        }

        return "导出成功";
    }

    @Override
    @Transactional
    public ResultVO save(Integer id, String remark) {
        ResultEnum resultEnum = validStatus(id);
        if(!resultEnum.equals(ResultEnum.SUCCESS)){
            log.info("修改对账单失败");
            return ResultVOUtil.fail(resultEnum);
        }
        CheckAccount checkAccount = checkAccountRepository.findById(id).get();
        checkAccount.setRemark(remark);

        checkAccountRepository.save(checkAccount);

        return ResultVOUtil.success();
    }

    private synchronized String checkCodeGenerator(String ym){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ym);
        Integer seq =1;
        Optional<CheckAccount> checkAccountOptional = checkAccountRepository.findFirstByCheckCodeStartingWithOrderByCheckCodeDesc(ym);
        if (checkAccountOptional.isPresent()){
            CheckAccount checkAccount = checkAccountOptional.get();
            String checkCode = checkAccount.getCheckCode();
            seq = new Integer(checkCode.substring(ym.length()))+1;
        }

        return stringBuilder.append(CommonUtil.addZero(seq,4)).toString();

    }

    private CheckAccountLine setCheckAccountLine(Order order,Integer row,Integer checkId,OrderLine orderLine){
        CheckAccountLine checkAccountLine = new CheckAccountLine();
        checkAccountLine.setCheckId(checkId);
        checkAccountLine.setRowNum(row);
        checkAccountLine.setOrderType(order.getOrderType());
        checkAccountLine.setCustomerCode(order.getCustomerCode());
        checkAccountLine.setCustomerName(order.getCustomerName());
        checkAccountLine.setOrderCode(order.getOrderNum());
        checkAccountLine.setActualQuantity(orderLine.getActualQuantity());
        checkAccountLine.setFinishDate(order.getFinishTime());
        checkAccountLine.setProductCode(orderLine.getProductCode());
        checkAccountLine.setProductName(orderLine.getProductName());
        checkAccountLine.setFinishPrice(orderLine.getFinishPrice());
        checkAccountLine.setSumPrice(orderLine.getFinishPrice().multiply(new BigDecimal(orderLine.getActualQuantity())));
        checkAccountLine.setUnit(orderLine.getUnit());
        return checkAccountLine;

    }


    private ResultEnum validStatus (Integer id){
        Optional<CheckAccount> checkAccountOptional = checkAccountRepository.findById(id);
        if (!checkAccountOptional.isPresent()){
            return ResultEnum.NOT_FIND_RECODE;
        }
        CheckAccount checkAccount = checkAccountOptional.get();
        if (!checkAccount.getStatus().equals("制单") ){
            return ResultEnum.VALID_ORDER_ERROR;
        }
        return ResultEnum.SUCCESS;
    }




}
