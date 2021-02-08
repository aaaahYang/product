package com.product.dao.repository;

import com.product.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>, JpaSpecificationExecutor {

    Optional<Customer> findByCustomerCode(String customerCode);

}
