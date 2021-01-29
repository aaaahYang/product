package com.product.entity.form;

import com.product.entity.Customer;
import com.product.entity.CustomerProduct;
import com.product.entity.vo.ValidList;

import java.util.List;

public class CustomerForm extends Customer {


    private List<CustomerProduct> customerProducts;

    public List<CustomerProduct> getCustomerProducts() {
        return customerProducts;
    }

    public void setCustomerProducts(List<CustomerProduct> customerProducts) {
        this.customerProducts = customerProducts;
    }
}
