package com.product.web.service;


import com.product.entity.Customer;
import com.product.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ComponentScan("com.product.service")
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Test
    public void findCustomerList(){

        Page<Customer> customerPage = customerService.findList("ct",null,PageRequest.of(0,2));

        List<Customer> list = customerPage.getContent();

        for (Customer customer : list){
            System.out.println(customer);
        }

    }

}
