package com.product.web.controller;


import com.product.entity.Customer;
import com.product.entity.CustomerProduct;
import com.product.entity.enums.ResultEnum;
import com.product.entity.form.CustomerForm;
import com.product.entity.vo.ValidList;
import com.product.service.CustomerService;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.web.util.PageResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private Validator validator;


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

        Page<Customer> customerPage = customerService.findList(customerCode,customerName, PageRequest.of(page-1,size, Sort.Direction.DESC,"customerId"));


        return ResultVOUtil.success(PageResultUtil.toResult(customerPage));
    }

    @RequestMapping("/find/{customerId}")
    public ResultVO findOneCustomer(@PathVariable Integer customerId){
        return customerService.findOne(customerId);
    }

    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO save(@Valid @RequestBody CustomerForm customerForm , BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }
        List<CustomerProduct> customerProducts = customerForm.getCustomerProducts();
        if (!customerProducts.isEmpty()){
            for (CustomerProduct customerProduct : customerProducts){
                Set<ConstraintViolation<CustomerProduct>> constraintViolations = validator.validate(customerProduct);
                for (ConstraintViolation constraintViolation : constraintViolations){
                    return ResultVOUtil.fail(ResultEnum.VALID_ERROR,constraintViolation.getMessage());
                }
            }
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerForm,customer);

        return customerService.save(customer,customerProducts);
    }

    @PostMapping("/delete")
    public ResultVO delete(Integer[] customerIds){

        /*for (int i =0 ; i<customerIds.length;i++){
            System.out.println(customerIds[i]);
        }

        return null;*/
        if (customerIds.length == 0){
            return ResultVOUtil.fail(ResultEnum.NOT_EMPTY);
        }

        return customerService.delete(customerIds);
    }

    @PostMapping("/deleteLine")
    public ResultVO deleteProductLine(Integer[] productLineIds){

        if(productLineIds.length == 0){
            return ResultVOUtil.fail(ResultEnum.NOT_EMPTY);
        }
        return customerService.deleteCustomerProduct(productLineIds);
    }




}
