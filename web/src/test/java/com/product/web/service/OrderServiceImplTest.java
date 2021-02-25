package com.product.web.service;

import com.alibaba.fastjson.JSON;
import com.product.entity.vo.OrderSearchVO;
import com.product.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    @Test
    public void findList() throws ParseException {

        OrderSearchVO orderSearchVO = new OrderSearchVO();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        orderSearchVO.setStartTime(simpleDateFormat.parse("2021-02-25"));
        orderSearchVO.setEndTime(simpleDateFormat.parse("2021-03-05"));
        orderSearchVO.setDateType("创建时间");
        Page page = orderService.findList(orderSearchVO, PageRequest.of(0,10));

        System.out.println(JSON.toJSON(page));




    }

}
