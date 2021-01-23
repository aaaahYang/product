package com.product.dao.repository;

import com.product.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Integer> , JpaSpecificationExecutor {

    Material findByMaterialCode(String materialCode);

}
