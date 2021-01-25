package com.product.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;


@SpringBootTest
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
class CustomerControllerTest  {

    //final RestDocumentationExtension restDocumentation = new RestDocumentationExtension("target/snippets");


    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation){

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void findCustomerList() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/list?customerCode=dj"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("customer/list",
                        requestParameters(parameterWithName("customerCode").description("客户编码 [可选]").optional(),
                                parameterWithName("customerName").description("客户名称 [可选]").optional(),
                                parameterWithName("page").description("页码 默认为1").optional(),
                                parameterWithName("size").description("显示记录数 默认为10").optional()),
                        responseFields(fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.OBJECT).optional(),
                                fieldWithPath("data.size").description("每页记录数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.currentPage").description("当前页码").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.totalPage").description("总页数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.totalElements").description("总记录数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[]").description("数据列表").type(JsonFieldType.ARRAY).optional(),
                                fieldWithPath("data.list[].customerId").description("客户ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[].customerName").description("客户名称").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].customerCode").description("客户编码").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].phoneNum").description("联系电话").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].address").description("地址").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].remark").description("备注").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].createTime").description("创建时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].updateTime").description("更新时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].operator").description("修改人").type(JsonFieldType.NUMBER).optional()
                        )
                ));

    }
}