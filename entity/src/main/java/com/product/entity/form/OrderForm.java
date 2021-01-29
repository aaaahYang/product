package com.product.entity.form;

import com.product.entity.Order;
import com.product.entity.OrderLine;

import java.util.List;

public class OrderForm extends Order {

    private List<OrderLine> orderLines ;

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
