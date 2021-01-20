package com.product.dao.repository;

import com.product.entity.CheckAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckAccountRepository extends JpaRepository<CheckAccount,Integer> {
}
