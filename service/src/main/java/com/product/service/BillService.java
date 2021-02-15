package com.product.service;

import com.product.entity.CheckAccount;
import com.product.entity.CheckAccountLine;
import com.product.entity.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.OutputStream;
import java.util.Date;

public interface BillService {

    Page<CheckAccount> findList(CheckAccount checkAccount, PageRequest pageRequest);

    ResultVO findOne(Integer id);

    Page<CheckAccountLine> findLine(Integer id ,PageRequest pageRequest);

    ResultVO create(String ym);

    ResultVO publish(Integer id);

    ResultVO delete(Integer id);

    String toExcel(OutputStream outputStream, Integer id);

    ResultVO save(Integer id,String remark);


}
