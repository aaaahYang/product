package com.product.dao.repository;

import com.product.entity.CheckAccountLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckAccountLineRepository extends JpaRepository<CheckAccountLine,Integer> {

    Page<CheckAccountLine> findByCheckIdOrderByRowNumAsc(Integer checkId, Pageable pageable);

    List<CheckAccountLine> findByCheckIdOrderByRowNumAsc(Integer checkId);

    Integer countByCheckId(Integer checkId);

    void deleteByCheckId(Integer checkId);


}
