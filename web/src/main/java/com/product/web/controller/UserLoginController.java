package com.product.web.controller;


import com.product.entity.Customer;
import com.product.entity.CustomerProduct;
import com.product.entity.dto.CustomerDTO;
import com.product.entity.form.CustomerForm;
import com.product.entity.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserLoginController {

    @PostMapping("/login")
    public Map login(){
        Map map = new HashMap();
        Map result = new HashMap();
        result.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsInJvbGUiOiJhZG1pbiIsIm5pY2tuYW1lIjoiYWRtaW4iLCJhdmF0YXIiOiJodHRwczpcL1wvd3d3LnlvdWJhb2Jhby54eXpcL21wdnVlLXJlc1wvbG9nby5qcGciLCJpc3MiOiJjb25zb2xlLmRvbmdmYW5nYmV0LmNvbSIsImlhdCI6MTYxMTU3NTEyMCwiZXhwIjoxNjExNTgyMzIwfQ.QMsomBgMTYqcV_0_orOQDdh3ZFJLLG87oOMw8sS4xUs");

        map.put("code",0);
        map.put("msg","success");
        map.put("result",result);

        return map;
    }

    @RequestMapping("/userInfo")
    public Map userInfo(){
        Map map = new HashMap();
        map.put("code",0);
        map.put("msg","success");
        Map result = new HashMap();
        result.put("id","3");
        result.put("username","nicker");
        result.put("role","admin");
        result.put("nickname","admin");
        result.put("avatar","https://www.youbaobao.xyz/mpvue-res/logo.jpg");
        result.put("iss","console.dongfangbet.com");
        result.put("iat",1611575738);
        result.put("exp",1612582938);
        result.put("roles", Arrays.asList("admin"));
        map.put("result",result);
        return map;
    }

    @RequestMapping("/beans")
    public void test(@RequestBody CustomerForm customerForm){

        System.out.println(customerForm.getCustomerCode());
       // System.out.println(customerForm.getCustomerProducts().get(0).getCustomerId());
    }


}
