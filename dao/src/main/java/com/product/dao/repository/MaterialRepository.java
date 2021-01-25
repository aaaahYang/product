package com.product.dao.repository;

import com.product.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Integer> , JpaSpecificationExecutor {

    Optional<Material> findByMaterialCode(String materialCode);

}
