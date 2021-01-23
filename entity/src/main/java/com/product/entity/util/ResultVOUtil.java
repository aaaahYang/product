package com.product.entity.util;

import com.product.entity.enums.ResultEnum;
import com.product.entity.vo.ResultVO;

public class ResultVOUtil {


    public static ResultVO success(Object o){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("请求成功");
        resultVO.setData(o);

        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO fail(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO fail(ResultEnum resultEnum){
        return fail(resultEnum.getCode(),resultEnum.getMsg());
    }

    public static ResultVO fail(ResultEnum resultEnum,String msg){
        return fail(resultEnum.getCode(),resultEnum.getMsg().concat(msg));
    }

}
