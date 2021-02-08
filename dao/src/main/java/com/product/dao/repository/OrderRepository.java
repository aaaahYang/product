package com.product.dao.repository;

import com.product.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> , JpaSpecificationExecutor {

    Optional<Order> findFirstByOrderNumStartingWithOrderByOrderNumDesc(String orderNum);

    @Query("select o from Order o where o.finishTime between ?1 and ?2 and o.orderType = ?3 order by o.orderId")
    List<Order> findByFinishTimeBetweenAndOrderTypeOrderByOrderId(Date start , Date end , String orderType);

}
