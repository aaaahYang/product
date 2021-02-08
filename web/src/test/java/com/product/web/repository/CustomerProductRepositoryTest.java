package com.product.web.repository;

import com.product.dao.repository.CustomerProductRepository;
import com.product.entity.dto.CustomerDTO;
import com.product.entity.dto.CustomerProductDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ExtendWith({SpringExtension.class})
public class CustomerProductRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CustomerProductRepository customerProductRepository;
    @Test
    public void test1(){
        List<CustomerProductDTO> customerProductDTOS = customerProductRepository.findWithProductByCustomerId(1);
        System.out.println(customerProductDTOS.get(0).getCustomerId());
    }

    @Test
    public void test2(){
        System.out.println(new Date());
    }


    @Test
    void test3(){
        Query query = entityManager.createQuery("select cp from CustomerProduct cp where cp.customerId = ?4");
        query.setParameter(4,5);
        query.getResultList();
    }

}
