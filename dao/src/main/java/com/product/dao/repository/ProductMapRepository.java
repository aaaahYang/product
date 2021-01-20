package com.product.dao.repository;

import com.product.entity.ProductMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMapRepository extends JpaRepository<ProductMap,Integer> {
}
