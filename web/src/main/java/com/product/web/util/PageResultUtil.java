package com.product.web.util;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class PageResultUtil {

    public static Map<String,Object> toResult(Page page){
        Map<String,Object> map = new HashMap();
        map.put("currentPage",page.getNumber()+1);
        map.put("size",page.getSize());
        map.put("totalPage",page.getTotalPages());
        map.put("totalElements",page.getTotalElements());
        map.put("list",page.getContent());
        return map;
    }
}
