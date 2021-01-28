package com.product.dao.repository;

import com.product.entity.CustomerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerProductRepository extends JpaRepository<CustomerProduct,Integer> {


    List<CustomerProduct> findByCustomerId(Integer customerId);

    @Query("select  cp.productLineId as productLineId ,cp.customerId as customerId,cp.productCode as productCode \n" +
            ", cp.price as price ,p.materialCode as materialCode ,p.unit as unit , p.sizeDescription as sizeDescription\n" +
            ",cp.remark as remark ,cp.createTime as createTime ,cp.updateTime as updateTime ,cp.operator as operator\n" +
            "from CustomerProduct cp inner join Product p on cp.productCode = p.productCode where cp.customerId = ?1 ")
    List<com.product.entity.dto.CustomerProductDTO> findWithProductByCustomerId(Integer customerId);

    Optional<CustomerProduct> findByCustomerIdAndAndProductCode(Integer customerId,String productCode);

    void deleteByCustomerId(Integer customerId);


}
