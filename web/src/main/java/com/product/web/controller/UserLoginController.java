package com.product.web.controller;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.product.dao.repository.UserRepository;
import com.product.entity.Customer;
import com.product.entity.CustomerProduct;
import com.product.entity.User;
import com.product.entity.dto.CustomerDTO;
import com.product.entity.enums.ResultEnum;
import com.product.entity.form.CustomerForm;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@RestController
public class UserLoginController {

    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private Validator validator;

    @Autowired
    private UserRepository userRepository ;

    /*@PostMapping("/login")
    public Map login(){
        Map map = new HashMap();
        Map result = new HashMap();
        result.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsInJvbGUiOiJhZG1pbiIsIm5pY2tuYW1lIjoiYWRtaW4iLCJhdmF0YXIiOiJodHRwczpcL1wvd3d3LnlvdWJhb2Jhby54eXpcL21wdnVlLXJlc1wvbG9nby5qcGciLCJpc3MiOiJjb25zb2xlLmRvbmdmYW5nYmV0LmNvbSIsImlhdCI6MTYxMTU3NTEyMCwiZXhwIjoxNjExNTgyMzIwfQ.QMsomBgMTYqcV_0_orOQDdh3ZFJLLG87oOMw8sS4xUs");

        map.put("code",0);
        map.put("msg","success");
        map.put("result",result);

        return map;
    }*/

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

    @PostMapping("/login")
    public Map jwtLogin(@RequestParam("account") String account,
                            @RequestParam("password") String password, HttpServletResponse response){


        Map resultMap = new HashMap();
        Map result = new HashMap();

        Optional<User> userOptional = userRepository.findByUserAccount(account);
        if (!userOptional.isPresent()){
            resultMap.put("code",ResultEnum.LOGIN_ERROR.getCode());
            resultMap.put("msg",ResultEnum.LOGIN_ERROR.getMsg());
            return resultMap;
        }
        User user = userOptional.get();
        String md5pwd = DigestUtils.md5Hex(password);
        if(!md5pwd.equals(user.getUserPwd())){
            resultMap.put("code",ResultEnum.LOGIN_ERROR.getCode());
            resultMap.put("msg",ResultEnum.LOGIN_ERROR.getMsg());
            return resultMap;
        }

        Map<String,Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,600);
        String token = JWT.create()
                .withHeader(map)
                .withClaim("userId",user.getUserId())
                .withClaim("userName",user.getUserName())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256("productJWT"));
        response.setHeader("token",token);

        resultMap.put("code",0);
        resultMap.put("msg","success");
        result.put("token",token);
        resultMap.put("result",result);

        log.info(user.getUserName()+"登录系统");


        return resultMap;

    }
    

    @PostMapping("/user/save")
    public ResultVO save(@RequestParam("action") String action ,
                          @RequestParam("user") String userJson){
        User temp = JSON.parseObject(userJson,User.class);
        if (temp == null) return ResultVOUtil.fail(ResultEnum.ERROR_REQUEST);

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(temp);
        for(ConstraintViolation constraintViolation : constraintViolations) {
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR, constraintViolation.getMessage());
        }


        Optional<User> userOptional = userRepository.findByUserAccount(temp.getUserAccount());
        User user;
        if (!userOptional.isPresent() && action.equals("add")){
            user = new User();
            user.setUserAccount(temp.getUserAccount());
            user.setUserName(temp.getUserName());
            user.setUserPwd(DigestUtils.md5Hex(temp.getUserPwd()));
        }else if (userOptional.isPresent() && action.equals("update")){
            user = userOptional.get();
            user.setUserName(temp.getUserName());
            user.setUserPwd(DigestUtils.md5Hex(temp.getUserPwd()));
        }else{
            return ResultVOUtil.fail(ResultEnum.NOT_FIND_RECODE);
        }
        userRepository.save(user);
        log.info("action:"+action+" ,user:"+user.getUserAccount());
        return ResultVOUtil.success();
    }



}
