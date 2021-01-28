package com.product.entity.dto;

import com.product.entity.CustomerProduct;

import java.util.Date;
import java.util.List;

public class CustomerDTO {

    /**
     * 客户ID
     */
    private Integer customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 联系电话
     */
    private String phoneNum;

    /**
     * 客户地址
     */
    private String address;

    /**
     * 客户备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private Integer operator;

    /**
     * 客户成品列表
     */
    private List<CustomerProductDTO> customerProductDTOS;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public List<CustomerProductDTO> getCustomerProductDTOS() {
        return customerProductDTOS;
    }

    public void setCustomerProductDTOS(List<CustomerProductDTO> customerProductDTOS) {
        this.customerProductDTOS = customerProductDTOS;
    }
}
