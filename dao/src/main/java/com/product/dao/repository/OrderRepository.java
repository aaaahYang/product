package com.product.dao.repository;

import com.product.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> , JpaSpecificationExecutor {

    Optional<Order> findFirstByOrderNumStartingWithOrderByOrderNumDesc(String orderNum);

}
