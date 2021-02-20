package com.product.web.handler;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.product.dao.repository.UserRepository;
import com.product.entity.User;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Optional;


public class LoginInterceptor implements HandlerInterceptor {


    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        crossDomain(request,response);
        String token = request.getHeader("Authorization");

        if(token != null && !token.isEmpty()){
            token = token.split(" ")[1];

            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("productJWT")).build();

            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Date expiresDate =  decodedJWT.getExpiresAt();

            if (expiresDate.getTime() < System.currentTimeMillis()){
                setResponse(response, ResultVOUtil.fail(-2,"登录过期"));
                return false;
            }
            return true;

        }
        setResponse(response, ResultVOUtil.fail(-2,"登录验证失败"));
        return false;
    }

    public void setResponse(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String json  = JSONObject.toJSONString(resultVO);
        writer.print(json);
        writer.close();
    }

    public void crossDomain(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
    }
}
