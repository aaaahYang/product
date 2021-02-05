package com.product.dao.repository;

import com.product.entity.CheckAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckAccountRepository extends JpaRepository<CheckAccount,Integer>, JpaSpecificationExecutor {

    Optional<CheckAccount> findFirstByCheckCodeStartingWithOrderByCheckCodeDesc(String checkCode);


}
