package com.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.product.dao.repository.MaterialRepository;
import com.product.dao.repository.unit.SpecificationUnit;
import com.product.entity.Material;
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.MaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;


@Service
public class MaterialServiceImpl implements MaterialService {


    private static final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);

    @Autowired
    MaterialRepository materialRepository;

    @Override
    public ResultVO save(Material material) {


        Optional<Material> optionalMaterial = materialRepository.findByMaterialCode(material.getMaterialCode());

        if (optionalMaterial.isPresent()) {

            Material m = optionalMaterial.get();

            if (!m.getMaterialId().equals(material.getMaterialId()) && m.getMaterialCode().equals(material.getMaterialCode())){
                logger.info("物料编码不允许重复，" + JSONObject.toJSONString(material));
                return ResultVOUtil.fail(ResultEnum.PARAM_REPEAT, "物料编码");
            }

        }

        materialRepository.save(material);


        return ResultVOUtil.success();
    }

    @Override
    public Page<Material> findList(Material material, PageRequest pageRequest) {

        Specification specification = new SpecificationUnit(material);


        return materialRepository.findAll(specification, pageRequest);
    }

    @Override
    public ResultVO delete(Integer[] materialIds) {

        for (Integer i : materialIds){
            try {

                materialRepository.deleteById(i);
            } catch (EmptyResultDataAccessException e) {
                logger.info("试图删除不存在的记录,materialId=" + i);
                return ResultVOUtil.fail(ResultEnum.NOT_FIND_RECODE);
            }
        }



        return ResultVOUtil.success();
    }
}
