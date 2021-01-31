package com.product.entity.enums;

public enum ResultEnum {

    SUCCESS(0,"请求成功"),
    VALID_ERROR(100,"字段校验错误，"),
    PARAM_REPEAT(101,"字段值不允许重复："),
    NOT_FIND_RECODE(102,"没找到记录"),
    NOT_EMPTY(103,"参数不能为空"),
    UPDATE_CUSTOMER_PRODUCT_ERROR(104,"更新客户成品列表出错，产品编号："),
    VALID_ORDER_ERROR(105,"只能操作“制单”状态的订单"),


    ;

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
