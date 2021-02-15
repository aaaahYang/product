package com.product.web.controller;


import com.alibaba.fastjson.JSON;
import com.product.entity.CheckAccount;
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.BillService;
import com.product.web.util.PageResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/bill")
public class BillController {

    private final static Logger log = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService billService;

    @RequestMapping("/list")
    public ResultVO findList(@RequestParam(value = "param",required = false) String param,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             @RequestParam(value = "page",defaultValue = "1") Integer page){
        CheckAccount checkAccount = JSON.parseObject(param,CheckAccount.class);
        if (checkAccount == null) checkAccount = new CheckAccount();
        return ResultVOUtil.success(PageResultUtil.toResult(billService.findList(checkAccount,PageRequest.of(page-1,size, Sort.Direction.DESC,"checkId"))));
    }

    @RequestMapping("/find/{checkId}")
    public ResultVO findOne(@PathVariable Integer checkId){
        return billService.findOne(checkId);
    }

    @RequestMapping("/findLine")
    public ResultVO findLine(@RequestParam(value = "id") Integer id,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             @RequestParam(value = "page",defaultValue = "1") Integer page){
        return ResultVOUtil.success(PageResultUtil.toResult(billService.findLine(id,PageRequest.of(page-1,size,Sort.Direction.ASC,"checkLineId"))));
    }

    @RequestMapping("/create")
    public ResultVO create(@RequestParam(value = "ym") String ym){
        return billService.create(ym);
    }

    @RequestMapping("/publish")
    public ResultVO publish(@RequestParam(value = "id") Integer id){

        return billService.publish(id);
    }

    @RequestMapping("/delete")
    public ResultVO delete(@RequestParam(value = "id") Integer id){
        return billService.delete(id);
    }

    @RequestMapping("/export")
    public String toExcel(HttpServletResponse response,@RequestParam("id") Integer id){

        response.setContentType("application/binary;charset=UTF-8");
        try {
            response.setHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode("对账单.xlsx","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
             return billService.toExcel(response.getOutputStream(),id);

        } catch (IOException e) {
            e.printStackTrace();
            return ResultEnum.EXPORT_EXCEL_ERROR.getMsg();
        }
    }

    @RequestMapping("/save")
    public ResultVO save(@RequestParam("id") Integer id,@RequestParam("remark") String remark){
        CheckAccount checkAccount = new CheckAccount();
        checkAccount.setCheckId(id);
        checkAccount.setRemark(remark);
        return billService.save(id,remark);
    }



}
