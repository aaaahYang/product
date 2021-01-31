package com.product.web.controller;


import com.alibaba.fastjson.JSON;
import com.product.entity.Order;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {

    private final static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/list")
    public ResultVO findList(@RequestParam(value = "param",required = false) String param,
                             @RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size){
        Order order = JSON.parseObject(param, Order.class);

        return ResultVOUtil.success(orderService.findList(order,
                PageRequest.of(page-1,size, Sort.Direction.DESC,"orderId")));
    }


}
