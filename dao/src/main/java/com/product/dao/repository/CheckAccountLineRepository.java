package com.product.dao.repository;

import com.product.entity.CheckAccountLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckAccountLineRepository extends JpaRepository<CheckAccountLine,Integer> {
}
