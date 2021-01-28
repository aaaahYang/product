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
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerProductRepository customerProductRepository;

    @Autowired
    private ProductRepository productRepository;

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

        if (!customerProducts.isEmpty()){

            for (CustomerProduct customerProduct:customerProducts){
                if (!customerRepository.existsById(customerProduct.getCustomerId())){
                    return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"找不到对应的客户ID");
                }
                Optional<Product> productOptional = productRepository.findByProductCode(customerProduct.getProductCode());
                if(!productOptional.isPresent()){
                    return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"找该 "+customerProduct.getProductCode()+" 不到产品编码");
                }
                customerProductRepository.save(customerProduct);
            }
        }
        customerRepository.save(customer);

        return ResultVOUtil.success();
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
    public ResultVO deleteCustomerProduct(Integer[] productLineIds) {

        for (Integer i: productLineIds){
            if(customerProductRepository.existsById(i))
                customerProductRepository.deleteById(i);
        }

        return ResultVOUtil.success();
    }
}
