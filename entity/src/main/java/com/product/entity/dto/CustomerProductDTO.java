package com.product.entity.dto;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Date;

public interface CustomerProductDTO {

    /**
     * @return customerProduct.ProductLineId
     */
    Integer getProductLineId();

    /**
     * @return customerProduct.CustomerId
     */
    Integer getCustomerId();

    /**
     * @return customerProduct.ProductCode
     */
    String getProductCode();

    /**
     * @return Product.ProductName
     */
    String getProductName();

    /**
     * @return customerProduct.Price
     */
    BigDecimal getPrice();

    /**
     * @return product.Unit
     */
    String  getUnit();

    /**
     * @return product.MaterialCode
     */
    String getMaterialCode();

    /**
     * @return product.SizeDescription
     */
    String getSizeDescription();

    /**
     * @return customerProduct.Remark
     */
    String getRemark();

    /**
     * @return customerProduct.CreateTime
     */
    Date getCreateTime();

    /**
     * @return customerProduct.UpdateTime
     */
    Date getUpdateTime();

    /**
     * @return customerProduct.Operator
     */
    Integer getOperator();




}
