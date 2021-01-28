package com.product.web.repository;

import com.product.dao.repository.CustomerProductRepository;
import com.product.entity.dto.CustomerProductDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith({SpringExtension.class})
public class CustomerProductRepositoryTest {

    @Autowired
    CustomerProductRepository customerProductRepository;
    @Test
    public void test1(){
        List<CustomerProductDTO> customerProductDTOS = customerProductRepository.findWithProductByCustomerId(1);
        System.out.println(customerProductDTOS.get(0).getCustomerId());
    }
}
