package com.product.web.controller;


import com.alibaba.fastjson.JSON;
import com.product.entity.Product;
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.ProductService;
import com.product.web.util.PageResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final static Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping("/list")
    public ResultVO findList(@RequestParam(value = "param",required = false) String param,
                                                 @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                 @RequestParam(value = "size",defaultValue = "10") Integer size){
        Product product = JSON.parseObject(param,Product.class);
        if(product == null) product = new Product();
        return ResultVOUtil.success(PageResultUtil.toResult(productService.findList(product, PageRequest.of(page-1,size, Sort.Direction.DESC,"productId"))));

    }

    @PostMapping("/save")
    public ResultVO save(@Valid Product product, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }
        log.info("成品变更,product:"+JSON.toJSONString(product));
        return productService.save(product);
    }

    @RequestMapping("/delete")
    public ResultVO delete(Integer[] productIds){
        log.info("删除成品,productIds:"+JSON.toJSONString(productIds));
        return productService.delete(productIds);
    }



    @PostMapping("/refresh")
    public ResultVO updateCustomerProduct(Integer customerId){
        log.info("更新客户成品，customerId:"+customerId);
        return productService.updateCustomerProduct(customerId);
    }

    @RequestMapping("/toExcel")
    public String toExcel(HttpServletResponse response , Integer[] productIds){

        response.setContentType("application/binary;charset=UTF-8");

        try {
            response.setHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode("成品导出.xlsx","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            log.info("开始导出成品EXCEL");
            return productService.toExcel(response.getOutputStream(), Arrays.asList(productIds)).getMsg();
        } catch (IOException e) {
            e.printStackTrace();
            return ResultEnum.EXPORT_EXCEL_ERROR.getMsg();
        }
    }

}
