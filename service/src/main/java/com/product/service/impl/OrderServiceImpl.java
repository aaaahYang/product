package com.product.service.impl;

import com.product.dao.repository.OrderLineRepository;
import com.product.dao.repository.OrderRepository;
import com.product.dao.repository.unit.SpecificationUnit;
import com.product.entity.Order;
import com.product.entity.OrderLine;
import com.product.entity.dto.OrderDTO;
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.OrderService;
import com.product.service.unit.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    private static final HashMap<String,String> ORDER_TYPE_MAP = new HashMap();

    static {
        ORDER_TYPE_MAP.put("退货单","TH");
        ORDER_TYPE_MAP.put("送货单","SH");
    }


    @Override
    public Page<Order> findList(Order order, PageRequest pageRequest) {

        SpecificationUnit<Order> specification = new SpecificationUnit<>(order);

        return orderRepository.findAll(specification,pageRequest);
    }

    @Override
    public ResultVO findOne(Integer orderId) {

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(!orderOptional.isPresent()){
            return ResultVOUtil.fail(ResultEnum.NOT_FIND_RECODE);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderOptional.get(),orderDTO);

        List<OrderLine> orderLines = orderLineRepository.findByOrderIdOrderByOrderLineId(orderId);
        orderDTO.setOrderLines(orderLines);


        return ResultVOUtil.success(orderDTO);
    }

    @Override
    @Transactional
    public ResultVO save(Order order, List<OrderLine> orderLines) {

        Integer orderId= order.getOrderId();
        String orderNum = order.getOrderNum();
        if(!validInt(orderId) && validStr(orderNum) || validInt(orderId) && !validStr(orderNum)){
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"保存时订单ID和订单编号不能为空");
        }
        BigDecimal sumPrice = new BigDecimal(0);
        if (orderLines != null && !orderLines.isEmpty()){

            for (OrderLine orderLine : orderLines){
                if(!orderId.equals(orderLine.getOrderId())){
                    return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"订单ID与明细不匹配");
                }
                if (orderLine.getFinishPrice() != null )
                    sumPrice = sumPrice.add(orderLine.getFinishPrice());
                orderLineRepository.save(orderLine);
            }
        }
        if(!validStr(orderNum)){

            orderNum = orderNumGenerator(ORDER_TYPE_MAP.get(order.getOrderType()));
            order.setOrderNum(orderNum);
        }
        order.setSumPrice(sumPrice);

        order = orderRepository.save(order);

        Map<String,Object> map = new HashMap<>();
        map.put("orderId",order.getOrderId());
        return ResultVOUtil.success(map);
    }

    @Override
    @Transactional
    public ResultVO delete(Integer orderId) {
        ResultEnum resultEnum = validOrderStatusA(orderId);

        if(!resultEnum.equals(ResultEnum.SUCCESS)){
            return ResultVOUtil.fail(resultEnum);
        }

        orderRepository.deleteById(orderId);


        return ResultVOUtil.success();
    }

    @Override
    @Transactional
    public ResultVO deleteLine(Integer[] lineIds) {

        for (Integer i : lineIds){

            Optional<OrderLine> optionalOrderLine = orderLineRepository.findById(i);
            if(optionalOrderLine.isPresent()){
                OrderLine orderLine = optionalOrderLine.get();
                ResultEnum resultEnum = validOrderStatusA(orderLine.getOrderId());

                if(!resultEnum.equals(ResultEnum.SUCCESS)){
                    return ResultVOUtil.fail(resultEnum);
                }
                orderLineRepository.deleteById(i);
            }
        }
        return ResultVOUtil.success();
    }


    @Override
    public ResultVO finish(Order order, List<OrderLine> orderLines) {
        ResultEnum resultEnum = validOrderStatusA(order.getOrderId());

        if(!resultEnum.equals(ResultEnum.SUCCESS)){
            return ResultVOUtil.fail(resultEnum);
        }
        if(orderLines.size() <= 0){
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"没找到行记录");
        }

        for (OrderLine orderLine : orderLines){
            if (orderLine.getActualQuantity() == null || orderLine.getActualQuantity().compareTo(0)< 0){
                return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"实际数量不能为空");
            }
            if (orderLine.getActualQuantity().compareTo(orderLine.getQuantity()) > 0){
                return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"实际数量不能多于订单数量");
            }

            if (orderLine.getFinishPrice() == null || orderLine.getFinishPrice().compareTo(new BigDecimal("0.0")) < 0){
                return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"最终价格不能为空");
            }

        }
        order.setFinishTime(new Date());
        order.setStatus("完结");

        return save(order,orderLines);
    }


    /**
     * 校验订单是否为 “制单” 状态
     * @param orderId
     * @return
     */
    private ResultEnum validOrderStatusA(Integer orderId){
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(!orderOptional.isPresent()){
            return ResultEnum.NOT_FIND_RECODE;
        }
        Order order = orderOptional.get();
        if (order.getStatus() == null || !order.getStatus().equals("制单")){
            return ResultEnum.VALID_ORDER_ERROR;
        }
        return ResultEnum.SUCCESS;
    }

    /**
     * 生成订单号
     * @param prefix 前缀  SH:送货单，TH:退货单
     * @return
     */
    private synchronized String orderNumGenerator(String prefix){

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(prefix);
        stringBuilder.append(getDate());
        Integer sequence ;
        Optional<Order> orderOptional = orderRepository.findFirstByOrderNumStartingWithOrderByOrderNumDesc(stringBuilder.toString());
        if(orderOptional.isPresent()){
           Order order = orderOptional.get();
           String orderNum = order.getOrderNum();
           sequence = new Integer(orderNum.substring(stringBuilder.length()))+1;
        }else{
            sequence = 1;
        }

        return stringBuilder.append(CommonUtil.addZero(sequence,4)).toString();
    }


    /**
     * 获取时间
     * @return
     */
    private String getDate(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }


    private Boolean validInt(Integer i){
        if(i == null || i < 0){
            return false;
        }
        return true;
    }

    private Boolean validStr(String s){
        if(s == null || s.isEmpty()){
            return false;
        }
        return true;
    }


}
