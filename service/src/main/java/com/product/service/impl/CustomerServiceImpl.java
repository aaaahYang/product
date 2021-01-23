package com.product.service.impl;

import com.product.dao.repository.CustomerProductRepository;
import com.product.dao.repository.CustomerRepository;
import com.product.dao.repository.unit.SpecificationUnit;
import com.product.entity.Customer;
import com.product.entity.dto.CustomerDTO;
import com.product.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerProductRepository customerProductRepository;

    @Override
    public Page<Customer> findList(String customerCode,String customerName,Pageable pageable) {

        //Page<Customer> page = customerRepository.findAll(pageable);
        Customer customer = new Customer();
        customer.setCustomerCode(customerCode);
        customer.setCustomerName(customerName);

        Specification<Customer> customerSpecification = new SpecificationUnit<>(customer);


        Page<Customer> page = customerRepository.findAll(customerSpecification,pageable);


        return page;
    }

    @Override
    public CustomerDTO findOne(Integer customerId) {
        return null;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public CustomerDTO delete(CustomerDTO customerDTO) {
        return null;
    }
}
