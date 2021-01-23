package com.product.web.controller;


import com.product.entity.Material;
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.MaterialService;
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
     * @param material
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO<Material> findList(@RequestParam(value = "material",required = false) Material material ,
                                       @RequestParam(value = "page",defaultValue = "1") Integer page,
                                       @RequestParam(value = "size",defaultValue = "10") Integer size){
        return ResultVOUtil.success(materialService.findList(material, PageRequest.of(page-1,size)));
    }


    @PostMapping("/save")
    public ResultVO save(@Valid Material material, BindingResult bindingResult){

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
