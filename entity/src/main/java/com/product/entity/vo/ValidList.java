package com.product.entity.vo;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


public class ValidList<E> {

    @Valid
    private List<E> list = new ArrayList<>();

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
