package com.product.web.controller;


import com.product.entity.Customer;
import com.product.service.CustomerService;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.web.util.PageResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;


    /**
     * 获取客户列表
     * @param customerCode
     * @param customerName
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<Customer>> findCustomerList(@RequestParam(value = "customerCode",required = false) String customerCode,
                                                     @RequestParam(value = "customerName",required = false) String customerName,
                                                     @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size",defaultValue = "10") Integer size){

        Page<Customer> customerPage = customerService.findList(customerCode,customerName, PageRequest.of(page-1,size));


        return ResultVOUtil.success(PageResultUtil.toResult(customerPage));
    }




}
