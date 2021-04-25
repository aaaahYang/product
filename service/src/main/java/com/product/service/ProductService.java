package com.product.service;

import com.product.entity.Product;
import com.product.entity.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.OutputStream;
import java.util.List;

public interface ProductService {

    /**
     * 获取产品列表
     * @param product     过滤使用，可选
     * @param pageRequest
     * @return
     */
    Page<Product> findList(Product product, PageRequest pageRequest);

    /**
     * 新增/修改
     * @param product
     * @return
     */
    ResultVO save(Product product);

    /**
     * 删除客户成品下对应成品
     * 删除产品列表对应产品
     * @param productIds
     * @return
     */
    ResultVO delete(Integer[] productIds);

    /**
     * 更新客户成品下的成品信息
     * @param customerId
     * @return
     */
    ResultVO updateCustomerProduct(Integer customerId);


    ResultVO toExcel(OutputStream outputStream, List<Integer> productIds);


}
