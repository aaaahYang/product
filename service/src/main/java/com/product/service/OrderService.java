package com.product.service;

import com.product.entity.Order;
import com.product.entity.OrderLine;
import com.product.entity.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface OrderService {

    /**
     * 查询订单列表
     * @param order
     * @param pageRequest
     * @return
     */
    Page<Order> findList(Order order, PageRequest pageRequest);

    /**
     * 查询订单明细
     * @param orderId
     * @return
     */
    ResultVO findOne(Integer orderId);

    /**
     * 保存
     * @param order
     * @param orderLines
     * @return
     */
    ResultVO save(Order order , List<OrderLine> orderLines);

    /**
     * 删除
     * @param orderId
     * @return
     */
    ResultVO delete(Integer orderId);

    /**
     * 删除明细行
     * @param lineIds
     * @return
     */
    ResultVO deleteLine(Integer[] lineIds);


    /**
     * 完结订单
     * @param order
     * @param orderLines
     * @return
     */
    ResultVO finish(Order order , List<OrderLine> orderLines);



}
