package com.product.service;

import com.product.entity.Customer;
import com.product.entity.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


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
    CustomerDTO findOne(Integer customerId);

    /**
     * 创建/保存 客户
     * @param customerDTO
     * @return
     */
    CustomerDTO save(CustomerDTO customerDTO);

    /**
     * 删除 客户
     * @param customerDTO
     * @return
     */
    CustomerDTO delete(CustomerDTO customerDTO);







}
