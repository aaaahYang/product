package com.product.web.controller;


import com.alibaba.fastjson.JSON;
import com.product.entity.Order;
import com.product.entity.OrderLine;
import com.product.entity.enums.ResultEnum;
import com.product.entity.form.OrderForm;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.OrderSearchVO;
import com.product.entity.vo.ResultVO;
import com.product.service.OrderService;
import com.product.web.util.PageResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/order")
public class OrderController {

    private final static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private Validator validator;

    @PostMapping("/list")
    public ResultVO findList(@RequestParam(value = "param",required = false) String param,
                             @RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size){
//        Order order = JSON.parseObject(param, Order.class);
//        if(order == null) order = new Order();
        OrderSearchVO order = JSON.parseObject(param,OrderSearchVO.class);
        if (order == null) order = new OrderSearchVO();
        return ResultVOUtil.success(PageResultUtil.toResult(orderService.findList(order,
                PageRequest.of(page-1,size, Sort.Direction.DESC,"orderId"))));
    }

    @RequestMapping("/find/{orderId}")
    public ResultVO findOne(@PathVariable Integer orderId){

        return orderService.findOne(orderId);
    }

    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO save(@Valid @RequestBody OrderForm orderForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }

        List<OrderLine> orderLines = orderForm.getOrderLines();

        if(orderLines != null && !orderLines.isEmpty()){
            Set<ConstraintViolation<OrderLine>> constraintViolations ;
            for(OrderLine orderLine : orderLines){
                constraintViolations = validator.validate(orderLine);
                for(ConstraintViolation constraintViolation : constraintViolations){
                    return ResultVOUtil.fail(ResultEnum.VALID_ERROR,constraintViolation.getMessage());
                }
            }
        }

        Order order = new Order();
        BeanUtils.copyProperties(orderForm,order);
        String action = orderForm.getAction();
        if (order.getStatus() == null) order.setStatus("制单");
        switch (action){
            case "save":
                log.info("订单变更,orderForm:"+JSON.toJSONString(orderForm));
                return orderService.save(order,orderLines);
            case "finish":
                log.info("订单发布,orderForm:"+JSON.toJSONString(orderForm));
                return orderService.finish(order,orderLines);
            default: return ResultVOUtil.fail(ResultEnum.ERROR_REQUEST);
        }
    }

    @PostMapping("/delete")
    public ResultVO delete(Integer orderId){

        log.info("订单删除,orderId:"+orderId);
        return orderService.delete(orderId);
    }

    @PostMapping("/deleteLine")
    public ResultVO deleteLine(Integer[] orderLineIds){

        log.info("订单行删除,orderLineIds:"+JSON.toJSONString(orderLineIds));
        return orderService.deleteLine(orderLineIds);
    }




}
