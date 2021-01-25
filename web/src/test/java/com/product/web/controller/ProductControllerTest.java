package com.product.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
class ProductControllerTest extends RestDocsControllerTest {

    @Test
    void findList() throws Exception {

        this.mockMvc.perform(post("/product/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("product/list",
                        requestParameters(parameterWithName("param").description("模糊查询使用，传入product对象json字符串,如{productCode:value,productName:value}").optional(),
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
                                fieldWithPath("data.list[].productId").description("产品ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[].productCode").description("产品编码").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].productName").description("产品名称").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].productDescription").description("产品描述").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].unit").description("单位，默认 件，预留").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].price").description("价格").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[].productCode").description("物料编号").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].sizeDescription").description("开料尺寸").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].createTime").description("创建时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].updateTime").description("更新时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].operator").description("修改人").type(JsonFieldType.NUMBER).optional()
                        )
                ));

    }

    @Test
    void save() throws Exception {

        this.mockMvc.perform(post("/product/save")
                .content(("{\"productCode\":\"aab01\"" +
                        ",\"productName\":\"产品ab01\"" +
                        ",\"price\":\"10.00\"" +
                        ",\"materialCode\":\"aj-2\"" +
                        ",\"sizeDescription\":\"10*10*10\"" +
                        "}").getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("product/save",
                        relaxedRequestParameters(parameterWithName("productId").description("id 创建时后台生成").optional(),
                                parameterWithName("productCode").description("产品编码，必填").optional(),
                                parameterWithName("productName").description("产品名称，必填").optional(),
                                parameterWithName("materialCode").description("物料编码，必填").optional(),
                                parameterWithName("price").description("默认价格，必填").optional(),
                                parameterWithName("sizeDescription").description("开料尺寸，必填").optional(),
                                parameterWithName("unit").description("单位，选填").optional(),
                                parameterWithName("productDescription").description("产品描述，选填").optional(),
                                parameterWithName("remark").description("备注，选填").optional()
                        ),
                        responseFields(fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.NULL).optional())
                ));
        
    }

    @Test
    void delete() throws Exception {

        this.mockMvc.perform(get("/product/delete").param("productId","2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("product/delete",
                        requestParameters(parameterWithName("productId").description("传入产品ID")),
                        responseFields(fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.NULL).optional())
                ));
        
    }
}