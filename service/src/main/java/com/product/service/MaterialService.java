package com.product.service;

import com.product.entity.Material;
import com.product.entity.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MaterialService {

    /**
     * insert or update
     * @param material
     * @return
     */
    ResultVO save(Material material);

    /**
     * 获取material列表
     * @param material
     * @param pageRequest
     * @return
     */
    Page<Material> findList(Material material, PageRequest pageRequest);

    /**
     * 删除
     * @param materialId
     * @return
     */
    ResultVO delete(Integer materialId);
}
