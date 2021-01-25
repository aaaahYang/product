package com.product.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.Parameters;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
class MaterialControllerTest  {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider provider){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .build();
    }

    @Test
    void findList() throws Exception {

        this.mockMvc.perform(post("/material/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("material/list",
                        requestParameters(parameterWithName("param").description("模糊查询使用，传入material对象json字符串,如{materialCode:value,materialName:value}").optional(),
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
                                fieldWithPath("data.list[].materialId").description("物料ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[].materialCode").description("物料编码").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].materialName").description("物料名称").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].materialType").description("物料类型，暂无使用，预留").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].status").description("状态，暂无使用，预留").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].remark").description("备注").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].createTime").description("创建时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].updateTime").description("更新时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].operator").description("修改人").type(JsonFieldType.NUMBER).optional()
                                )
                ));
    }
    @Test
    void save() throws Exception {

        this.mockMvc.perform(post("/material/save")
                .content("{\"materialCode\":\"aj-8\",\"materialName\":\"铜八\"}".getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("material/save",
                        relaxedRequestParameters(parameterWithName("materialId").description("id 创建时后台生成").optional(),
                                parameterWithName("materialCode").description("物料编码，必填").optional(),
                                parameterWithName("materialName").description("物料名称，必填").optional(),
                                parameterWithName("remark").description("备注，选填").optional()
                                ),
                        responseFields(fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.NULL).optional())
                ));
    }

    @Test
    void delete() throws Exception {
        this.mockMvc.perform(get("/material/delete").param("materialId","7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("material/delete",
                        requestParameters(parameterWithName("materialId").description("传入物料ID")),
                        responseFields(fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.NULL).optional())
                        ));
    }
}