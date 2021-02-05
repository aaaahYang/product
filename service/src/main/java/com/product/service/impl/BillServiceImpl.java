package com.product.service.impl;

import com.product.dao.repository.CheckAccountLineRepository;
import com.product.dao.repository.CheckAccountRepository;
import com.product.dao.repository.unit.SpecificationUnit;
import com.product.entity.CheckAccount;
import com.product.entity.CheckAccountLine;
import com.product.entity.enums.ResultEnum;
import com.product.entity.util.ResultVOUtil;
import com.product.entity.vo.ResultVO;
import com.product.service.BillService;
import com.product.service.unit.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BillServiceImpl implements BillService {

    private final static Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    CheckAccountRepository checkAccountRepository;

    @Autowired
    CheckAccountLineRepository checkAccountLineRepository;

    @Override
    public Page<CheckAccount> findList(CheckAccount checkAccount, PageRequest pageRequest) {

        SpecificationUnit<CheckAccount> specificationUnit = new SpecificationUnit(checkAccount);

        return checkAccountRepository.findAll(specificationUnit,pageRequest);
    }

    @Override
    public ResultVO findOne(Integer id ) {

        Optional<CheckAccount> checkAccountOptional = checkAccountRepository.findById(id);
        if (!checkAccountOptional.isPresent()){
            return ResultVOUtil.fail(ResultEnum.NOT_FIND_RECODE);
        }

        return ResultVOUtil.success(checkAccountOptional.get());
    }

    public Page<CheckAccountLine> findLine(Integer id,PageRequest pageRequest){

        Optional<CheckAccount> checkAccountOptional = checkAccountRepository.findById(id);
        if(!checkAccountOptional.isPresent()){
            return null;
        }

        return checkAccountLineRepository.findByCheckIdOrderByRowNumAsc(id,pageRequest);

    }

    @Override
    @Transactional
    public ResultVO create(String ym) {

        if(ym.length() != 6 || !CommonUtil.isNumeric(ym)){
            return ResultVOUtil.fail(ResultEnum.VALID_ERROR,"传入6位长度年月，如：202102");
        }

        String year = ym.substring(0,4);
        String mm = ym.substring(4,6);

        String checkCode = checkCodeGenerator(ym);

        CheckAccount checkAccount = new CheckAccount();
        checkAccount.setCheckCode(checkCode);
        checkAccount.setYears(year);
        checkAccount.setMonths(mm);
        checkAccount.setStatus("制单");

        checkAccount.setOperator(1);
        checkAccount = checkAccountRepository.save(checkAccount);

        Integer checkId = checkAccount.getCheckId();



        return null;
    }

    @Override
    public ResultVO publish(Integer id) {
        return null;
    }

    @Override
    public ResultVO delete(Integer id) {
        return null;
    }

    @Override
    public ResultVO toExcel(Integer id) {
        return null;
    }


    private synchronized String checkCodeGenerator(String ym){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ym);
        Integer seq =1;
        Optional<CheckAccount> checkAccountOptional = checkAccountRepository.findFirstByCheckCodeStartingWithOrderByCheckCodeDesc(ym);
        if (checkAccountOptional.isPresent()){
            CheckAccount checkAccount = checkAccountOptional.get();
            String checkCode = checkAccount.getCheckCode();
            seq = new Integer(checkCode.substring(ym.length()+1));
        }

        return stringBuilder.append(CommonUtil.addZero(seq,4)).toString();

    }



}
