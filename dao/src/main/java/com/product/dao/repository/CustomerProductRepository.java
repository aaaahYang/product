package com.product.dao.repository;

import com.product.entity.CustomerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProductRepository extends JpaRepository<CustomerProduct,Integer> {
}
