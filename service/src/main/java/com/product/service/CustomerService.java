package com.product.service;

import com.product.entity.Customer;
import com.product.entity.CustomerProduct;
import com.product.entity.dto.CustomerDTO;
import com.product.entity.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CustomerService {

    /**
     * 获取 客户列表
     * @param pageable
     * @return
     */
    Page<Customer> findList(String customerCode,String customerName,Pageable pageable);

    /**
     * 获取 单个客户详细
     * @param customerId
     * @return
     */
    ResultVO<CustomerDTO> findOne(Integer customerId);

    /**
     * 创建/保存 客户
     * @param customer
     * @param customerProducts
     * @return
     */
    ResultVO save(Customer customer, List<CustomerProduct> customerProducts);

    /**
     * 删除 客户 可批量
     * @param customerIds
     * @return
     */
    ResultVO delete(Integer[] customerIds);

    ResultVO deleteCustomerProduct(Integer[] productLineIds);







}
