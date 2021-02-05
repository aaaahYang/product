package com.product.web.service;

import com.product.service.BillService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BillServiceTest {

    @Autowired
    private BillService billService;

    @Test
    public void create(){
        billService.create("202101");
    }


}
