package com.product.service.impl;

import com.product.dao.repository.CustomerProductRepository;
import com.product.dao.repository.CustomerRepository;
import com.product.dao.repository.ProductRepository;
import com.product.dao.repository.unit.SpecificationUnit;
import com.product.entity.Customer;
import com.product.entity.CustomerProduct;
import com.product.entity.Product;
import com.product.entity.dto.CustomerDTO;
import com.product.entity.dto.CustomerProductDTO;
import com.product.entity.dto.CustomerProductDTO2;
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.CustomerService;
import com.product.service.unit.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerProductRepository customerProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Customer> findList(String customerCode,String customerName,Pageable pageable) {

        //Page<Customer> page = customerRepository.findAll(pageable);
        Customer customer = new Customer();
        customer.setCustomerCode(customerCode);
        customer.setCustomerName(customerName);

        Specification<Customer> customerSpecification = new SpecificationUnit<>(customer);


        Page<Customer> page = customerRepository.findAll(customerSpecification,pageable);


        return page;
    }

    @Override
    public ResultVO<CustomerDTO> findOne(Integer customerId) {

        CustomerDTO customerDTO = new CustomerDTO();

        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (!customerOptional.isPresent()){
            return ResultVOUtil.fail(ResultEnum.NOT_FIND_RECODE);
        }

        BeanUtils.copyProperties(customerOptional.get(),customerDTO);

        List<CustomerProductDTO> customerProductList = customerProductRepository.findWithProductByCustomerId(customerId);

        customerDTO.setCustomerProductDTOS(customerProductList);

        return ResultVOUtil.success(customerDTO);
    }

    @Override
    @Transactional
    public ResultVO save(Customer customer, List<CustomerProduct> customerProducts) {

        if (customer.getCustomerId() == null && customer.getCustomerCode() != null){

            Optional<Customer> customerOptional = customerRepository.findByCustomerCode(customer.getCustomerCode());
            if (customerOptional.isPresent()){
                log.info("变更客户失败,客户编码已存在:"+customer.getCustomerCode());
                return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"客户编号已存在");
            }
        }
        Map<String,Object> map  = new HashMap<>();
        if (customerProducts != null && !customerProducts.isEmpty()){
            Optional<Product> productOptional;
            for (CustomerProduct customerProduct:customerProducts){
                if (!customerRepository.existsById(customerProduct.getCustomerId())){
                    log.info("变更客户失败，找不到对应的客户ID:"+customerProduct.getCustomerId());
                    return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"找不到对应的客户ID");
                }
                productOptional = productRepository.findByProductCode(customerProduct.getProductCode());
                if(!productOptional.isPresent()){
                    log.info("变更客户失败，找不到对应的成品编码:"+customerProduct.getProductCode());
                    return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"找该 "+customerProduct.getProductCode()+" 不到成品编码");
                }
                if (customerProduct.getProductLineId() == null && customerProduct.getProductCode() != null){
                    Optional<CustomerProduct> customerProductOptional = customerProductRepository.findByCustomerIdAndAndProductCode(customerProduct.getCustomerId(),customerProduct.getProductCode());
                    if (customerProductOptional.isPresent()){
                        log.info("变更客户失败，该客户已存在相同的产品编码:"+customerProduct.getProductCode());
                        return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"该客户已存在相同的产品编码："+customerProduct.getProductCode());
                    }
                }

                customerProduct = customerProductRepository.save(customerProduct);
                map.put("productLineId",customerProduct.getProductLineId());
            }
        }
        customer = customerRepository.save(customer);


        map.put("customerId",customer.getCustomerId());
        return ResultVOUtil.success(map);
    }

    @Override
    @Transactional
    public ResultVO delete(Integer[] customerIds) {

        for(Integer i : customerIds){
            if(customerRepository.existsById(i)){
                customerProductRepository.deleteByCustomerId(i);

                customerRepository.deleteById(i);
            }

        }

        return ResultVOUtil.success();
    }

    @Override
    @Transactional
    public ResultVO deleteCustomerProduct(Integer[] productLineIds) {

        for (Integer i: productLineIds){
            if(customerProductRepository.existsById(i))
                customerProductRepository.deleteById(i);
        }

        return ResultVOUtil.success();
    }

    @Override
    public List<CustomerProductDTO> findProductList(Integer customerId, Product product) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(" select  cp.product_Line_Id as product_Line_Id ,cp.customer_Id as customer_Id,cp.product_Code as product_Code , p.product_Name as product_Name " +
                ", cp.price as price ,p.material_Code as material_Code ,p.unit as unit , p.size_Description as size_Description " +
                ",cp.remark as remark ,cp.create_Time as create_Time ,cp.update_Time as update_Time ,cp.operator as operator " +
                " from Customer_Product cp inner join Product p on cp.product_Code = p.product_Code " +
                " where cp.customer_Id = ?1 ");
        if (CommonUtil.validStr(product.getProductCode())){
            queryBuilder.append(" and p.product_Code like ?2 ");
        }
        if(CommonUtil.validStr(product.getProductName()) ){
            queryBuilder.append(" and p.product_Name like ?3 ");
        }

        Query query = entityManager.createNativeQuery(queryBuilder.toString(), CustomerProductDTO2.class);

        query.setParameter(1,customerId);
        if (CommonUtil.validStr(product.getProductCode())){
            query.setParameter(2,"%"+product.getProductCode()+"%");
        }
        if(CommonUtil.validStr(product.getProductName()) ){
            query.setParameter(3,"%"+product.getProductName()+"%");
        }

        return query.getResultList();

    }
}
