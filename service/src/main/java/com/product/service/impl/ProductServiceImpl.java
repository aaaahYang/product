package com.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.product.dao.repository.CustomerProductRepository;
import com.product.dao.repository.MaterialRepository;
import com.product.dao.repository.ProductRepository;
import com.product.dao.repository.unit.SpecificationUnit;
import com.product.entity.CustomerProduct;
import com.product.entity.Material;
import com.product.entity.Product;
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.ProductService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CustomerProductRepository customerProductRepository;

    @Override
    public Page<Product> findList(Product product, PageRequest pageRequest) {

        SpecificationUnit<Product> specificationUnit = new SpecificationUnit<>(product);

        return productRepository.findAll(specificationUnit,pageRequest);
    }

    @Override
    @Transactional
    public ResultVO save(Product product) {

        Optional<Material> optionalMaterial = materialRepository.findByMaterialCode(product.getMaterialCode());
        if(!optionalMaterial.isPresent()){
            log.info("变更成品失败，没找到对应的物料编号:"+product.getMaterialCode());
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"物料编号 "+product.getMaterialCode()+" 不存在");
        }

        Optional<Product> optionalProduct = productRepository.findByProductCode(product.getProductCode());

        if (optionalProduct.isPresent()) {

            Product m = optionalProduct.get();

            if (!m.getProductId().equals(product.getProductId()) && m.getProductCode().equals(product.getProductCode())){
                log.info("成品编码不允许重复，" + JSONObject.toJSONString(product));
                return ResultVOUtil.fail(ResultEnum.PARAM_REPEAT, "产品编码");
            }

        }

        productRepository.save(product);

        return ResultVOUtil.success();
    }

    /**
     * 1.删除客户成品列表
     * 2.删除产品列表
     * @param productIds
     * @return
     */
    @Override
    @Transactional
    public ResultVO delete(Integer[] productIds) {

        for(Integer i : productIds){
            Optional<Product> optionalProduct = productRepository.findById(i);
            if(optionalProduct.isPresent()){
                customerProductRepository.deleteByProductCode(optionalProduct.get().getProductCode());
                productRepository.deleteById(i);
            }
        }

        return ResultVOUtil.success();

    }

    @Override
    @Transactional
    public ResultVO updateCustomerProduct(Integer customerId) {

            List<CustomerProduct> list = customerProductRepository.findByCustomerId(customerId);
            for (CustomerProduct customerProduct : list){
                String productCode = customerProduct.getProductCode();
                Optional<Product> optionalProduct = productRepository.findByProductCode(productCode);
                if(!optionalProduct.isPresent()){
                    log.info("刷新客户成品失败");
                    return ResultVOUtil.fail(ResultEnum.UPDATE_CUSTOMER_PRODUCT_ERROR,productCode);
                }
                Product product = optionalProduct.get();
                customerProduct.setPrice(product.getPrice());
            }
            customerProductRepository.saveAll(list);

        return ResultVOUtil.success();
    }

    @Override
    public ResultVO toExcel(OutputStream outputStream, List<Integer> productIds) {
        List<Product> result ;



        if (productIds.size() <= 0){
            result = productRepository.findAll();
        }else{
            result = productRepository.findAllById(productIds);
        }

        String[] strings = {"成品名称","成品编号","描述","单位","开料尺寸","使用物料"};

        SXSSFWorkbook workbook = new SXSSFWorkbook();

        SXSSFSheet sheet = workbook.createSheet();

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
        int i =0;
        for (Product product : result){
            sxssfRow = sheet.createRow(i+1);

            sxssfRow.createCell(0).setCellValue(product.getProductName());
            sxssfRow.createCell(1).setCellValue(product.getProductCode());
            sxssfRow.createCell(2).setCellValue(product.getProductDescription());
            sxssfRow.createCell(3).setCellValue(product.getUnit());
            sxssfRow.createCell(4).setCellValue(product.getSizeDescription());
            sxssfRow.createCell(5).setCellValue(product.getMaterialCode());

        }

        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.info("导出成品失败");
            e.printStackTrace();
            return ResultVOUtil.fail(ResultEnum.EXPORT_EXCEL_ERROR);
        }

        return ResultVOUtil.success();
    }
}
