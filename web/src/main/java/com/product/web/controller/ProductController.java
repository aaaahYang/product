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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return ResultVOUtil.success(PageResultUtil.toResult(productService.findList(product, PageRequest.of(page-1,size))));

    }

    @PostMapping("/save")
    public ResultVO save(@Valid @RequestBody Product product, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }
        return productService.save(product);
    }

    @RequestMapping("/delete")
    public ResultVO delete(Integer productId){
        return productService.delete(productId);
    }


}
