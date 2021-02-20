package com.product.web.handler;


import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final Integer RESULT_CODE = -2;

    @ExceptionHandler(Exception.class)
    public ResultVO exceptionHandler(HttpServletRequest request,Exception e){
        log.error("发生异常：",e);

        return ResultVOUtil.fail(RESULT_CODE,"请求失败，发生未知异常");
    }
}
