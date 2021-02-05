package com.product.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith({SpringExtension.class})
class WebApplicationTests {

    @Test
    void contextLoads() {

        String ym = "202102";
        System.out.println(ym.substring(0,4));
        System.out.println(ym.substring(4,6));

    }

}
