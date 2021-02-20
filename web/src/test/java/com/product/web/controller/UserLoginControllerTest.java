package com.product.web.controller;


import com.alibaba.fastjson.JSON;
import com.product.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest
public class UserLoginControllerTest extends RestDocsControllerTest {

    @Test
    void save() throws Exception{

        User user = new User();
        user.setUserName("管理员");
        user.setUserAccount("admin");
        user.setUserPwd("1234");

        this.mockMvc.perform(post("/user/save").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("action","update").param("user", JSON.toJSONString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/user/save",
                        requestParameters(
                                parameterWithName("action").description("操作，新增：add ; 修改：update"),
                                parameterWithName("user").description("传入json: {userAccount:\"admin\",userName:\"管理员\",userPwd:\"1234\"}")
                        ),
                        responseFields(
                                fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.NULL).optional()
                        )
                ));
    }



}
