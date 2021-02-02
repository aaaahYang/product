package com.product.entity.form;

import com.product.entity.Order;
import com.product.entity.OrderLine;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class OrderForm extends Order {

    /**
     * 操作： save or finish
     */
    @NotEmpty(message = "需要传入操作方式")
    private String action;

    private List<OrderLine> orderLines ;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
