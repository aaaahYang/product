package com.product.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.product.entity.Material;
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.MaterialService;
import com.product.web.util.PageResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/material")
public class MaterialController {

    private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);

    @Autowired
    MaterialService materialService;

    /**
     * 获取物料列表
     * @param param material json
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/list")
    public ResultVO<Material> findList(@RequestParam(value = "param",required = false) String param ,
                                       @RequestParam(value = "page",defaultValue = "1") Integer page,
                                       @RequestParam(value = "size",defaultValue = "10") Integer size){
        Material material = JSON.parseObject(param,Material.class);
        if (material ==null) material = new Material();

        return ResultVOUtil.success(PageResultUtil.toResult(materialService.findList(material, PageRequest.of(page-1,size))));
    }


    @PostMapping("/save")
    public ResultVO save(@Valid @RequestBody Material material, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }
        return materialService.save(material);
    }

    @RequestMapping("/delete")
    public ResultVO delete(Integer materialId){
        return materialService.delete(materialId);
    }


}
