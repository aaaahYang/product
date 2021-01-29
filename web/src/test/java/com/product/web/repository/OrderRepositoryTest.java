package com.product.web.repository;

import com.product.dao.repository.OrderRepository;
import com.product.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;


    @Test
    void findTopByOrderNumStartingWithOrderByOrderNumDesc(){
        Optional<Order> orderOptional =  orderRepository.findFirstByOrderNumStartingWithOrderByOrderNumDesc("SH20210129");

//        System.out.println(orderOptional.isPresent());
        System.out.println(orderOptional.get().getOrderNum());
    }
}