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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

        return checkAccountLineRepository.findByCheckIdOrderByRowNumAsc(id,pageRequest);

    }

    @Override
    @Transactional
    public ResultVO create(String ym) {

        if(ym.length() != 6 || !CommonUtil.isNumeric(ym)){
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
        int row  = 1;
        for(Order order : shList){
            List<OrderLine> orderLines = orderLineRepository.findByOrderIdOrderByOrderLineId(order.getOrderId());
            for (OrderLine orderLine : orderLines){
                checkAccountLine = setCheckAccountLine(order,row,checkId,orderLine);
                checkAccountLines.add(checkAccountLine);
                sumPrice = sumPrice.add(orderLine.getFinishPrice());
                row++;
            }
        }
        List<Order> thList = orderRepository.findByFinishTimeBetweenAndOrderTypeOrderByOrderId(start,end,"退货单");
        for(Order order : thList){
            List<OrderLine> orderLines = orderLineRepository.findByOrderIdOrderByOrderLineId(order.getOrderId());
            for (OrderLine orderLine : orderLines){
                checkAccountLine = setCheckAccountLine(order,row,checkId,orderLine);

                checkAccountLines.add(checkAccountLine);
                sumPrice = sumPrice.subtract(orderLine.getFinishPrice());
                row++;
            }
        }

        checkAccountLineRepository.saveAll(checkAccountLines);
        checkAccount.setSumPrice(sumPrice);
        checkAccountRepository.save(checkAccount);

        return ResultVOUtil.success();
    }

    @Override
    public ResultVO publish(Integer id) {
        Optional<CheckAccount> checkAccountOptional = checkAccountRepository.findById(id);
        if (!checkAccountOptional.isPresent()){
            return ResultVOUtil.fail(ResultEnum.NOT_FIND_RECODE);
        }
        CheckAccount checkAccount = checkAccountOptional.get();
        if (!checkAccount.getStatus().equals("制单") ){
            return ResultVOUtil.fail(ResultEnum.VALID_ORDER_ERROR);
        }

        if (checkAccountLineRepository.countByCheckId(id)<=0){
            return ResultVOUtil.fail(ResultEnum.BILL_PUBLISH_ERROR);
        }
        checkAccount.setStatus("已发布");
        checkAccountRepository.save(checkAccount);


        return ResultVOUtil.success();
    }

    @Override
    @Transactional
    public ResultVO delete(Integer id) {

        if (!checkAccountRepository.existsById(id)){
            return ResultVOUtil.fail(ResultEnum.NOT_FIND_RECODE);
        }
        checkAccountLineRepository.deleteByCheckId(id);
        checkAccountRepository.deleteById(id);

        return ResultVOUtil.success();
    }

    @Override
    public ResultVO toExcel(Integer id) {


        return null;
    }


    private synchronized String checkCodeGenerator(String ym){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ym);
        Integer seq =1;
        Optional<CheckAccount> checkAccountOptional = checkAccountRepository.findFirstByCheckCodeStartingWithOrderByCheckCodeDesc(ym);
        if (checkAccountOptional.isPresent()){
            CheckAccount checkAccount = checkAccountOptional.get();
            String checkCode = checkAccount.getCheckCode();
            seq = new Integer(checkCode.substring(ym.length()+1));
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
        checkAccountLine.setUnit(orderLine.getUnit());
        return checkAccountLine;

    }



}
