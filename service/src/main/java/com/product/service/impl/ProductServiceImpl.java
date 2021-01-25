package com.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.product.dao.repository.MaterialRepository;
import com.product.dao.repository.ProductRepository;
import com.product.dao.repository.unit.SpecificationUnit;
import com.product.entity.Material;
import com.product.entity.Product;
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Page<Product> findList(Product product, PageRequest pageRequest) {

        SpecificationUnit<Product> specificationUnit = new SpecificationUnit<>(product);

        return productRepository.findAll(specificationUnit,pageRequest);
    }

    @Override
    public ResultVO save(Product product) {

        Optional<Material> optionalMaterial = materialRepository.findByMaterialCode(product.getMaterialCode());
        if(!optionalMaterial.isPresent()){
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"物料编号 "+product.getMaterialCode()+" 不存在");
        }

        Optional<Product> optionalProduct = productRepository.findByProductCode(product.getProductCode());

        if (optionalProduct.isPresent()) {

            Product m = optionalProduct.get();

            if (!m.getProductId().equals(product.getProductId()) && m.getMaterialCode().equals(product.getMaterialCode())){
                log.info("产品编码不允许重复，" + JSONObject.toJSONString(product));
                return ResultVOUtil.fail(ResultEnum.PARAM_REPEAT, "产品编码");
            }

        }

        productRepository.save(product);

        return ResultVOUtil.success();
    }

    /**
     * 1.删除客户成品列表 todo
     * 2.删除产品列表
     * @param productId
     * @return
     */
    @Override
    public ResultVO delete(Integer productId) {

        try {
            productRepository.deleteById(productId);
        } catch (EmptyResultDataAccessException e) {
            log.info("试图删除不存在的记录,productId=" + productId);
            return ResultVOUtil.fail(ResultEnum.NOT_FIND_RECODE);
        }


        return ResultVOUtil.success();

    }
}