package com.product.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
public class Order {

    @Id
    private Integer orderId;

    private String orderType;

    private String orderNum;

    private String status;

    private String customerCode;

    private String customerName;

    private String deliveryAddress;

    private String sendAddress;

    private BigDecimal sumPrice;

    private String remark;

    private String phoneNum;

    private Date confirmTime;

    private Date finishTime;

    private Date createTime;

    private Date updateTime;

    private Integer operator;
}
