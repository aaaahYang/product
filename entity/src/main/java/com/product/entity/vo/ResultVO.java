package com.product.entity.vo;

public class ResultVO<T> {
    /**
     * 自定义状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * data
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
