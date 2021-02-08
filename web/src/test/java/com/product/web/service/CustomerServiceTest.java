package com.product.web.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.product.entity.Customer;
import com.product.entity.CustomerProduct;
import com.product.entity.Product;
import com.product.entity.dto.CustomerDTO;
import com.product.entity.dto.CustomerProductDTO;
import com.product.entity.form.CustomerForm;
import com.product.entity.vo.ValidList;
import com.product.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import javax.validation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ComponentScan("com.product.service")
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    Validator globalValidator;

    @Test
    public void findCustomerList(){

        Page<Customer> customerPage = customerService.findList("ct",null,PageRequest.of(0,2));

        List<Customer> list = customerPage.getContent();

        for (Customer customer : list){
            System.out.println(customer);
        }

    }

    @Test
    public void findOne(){
        Object jsonStr = JSONObject.toJSON(customerService.findOne(1));

        System.out.println(jsonStr.toString());
    }

    @Test
    public void BeanUtilsTest(){

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setAddress("123");
        customerDTO.setCustomerCode("123");
        customerDTO.setCustomerId(12);
        customerDTO.setCustomerName("1df夺嫡");
        customerDTO.setPhoneNum("12344425");


        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        System.out.println(customer.getCustomerCode());

    }

    @Test
    public void ValidationTest(){
        CustomerForm customerForm = new CustomerForm();
        List<CustomerProduct> list  = new ArrayList<>();
        CustomerProduct customerProduct = new CustomerProduct();
       // customerProduct.setCustomerId(123);
        //customerProduct.setProductCode(null);
        customerProduct.setPrice(new BigDecimal(124.2));
        list.add(customerProduct);
        ValidList<CustomerProduct> productValidList = new ValidList<>();
        productValidList.setList(list);
    //    customerForm.setCustomerProducts(productValidList);

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();

        try {
            Validator validator = vf.getValidator();
            Set<ConstraintViolation< CustomerProduct>> set = globalValidator.validate(customerProduct);
            for(ConstraintViolation<CustomerProduct> constraintViolation : set){
                System.out.println(constraintViolation.getMessage());
            }
            String message = set.stream().map(ConstraintViolation::getMessage).reduce((m1,m2) -> m1 + ";" + m2).orElse("参数输入有误！");
            System.out.println(message);
        }catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    void findProductList(){

        Product product  = new Product();
        product.setProductCode("1231124");
        List list  = customerService.findProductList(5,product);
        System.out.println(list);
    }


}
