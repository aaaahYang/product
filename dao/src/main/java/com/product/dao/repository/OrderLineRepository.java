package com.product.dao.repository;

import com.product.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine,Integer> {

    List<OrderLine> findByOrderIdOrderByOrderLineId(Integer orderId);

    Integer countByOrderId(Integer orderId);
}
