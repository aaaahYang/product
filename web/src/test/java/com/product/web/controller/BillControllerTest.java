package com.product.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest
class BillControllerTest extends RestDocsControllerTest {

    @Test
    void findList() throws Exception{

        this.mockMvc.perform(get("/bill/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/bill/list",
                        requestParameters(parameterWithName("param").description("模糊查询使用，传入order对象json字符串,如{checkCode:value},界面显示搜索字段：对账单编号，年，月").optional(),
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
                                fieldWithPath("data.list[].checkId").description("对账单ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[].checkCode").description("对账单编号").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].years").description("年").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].months").description("月").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].sumPrice").description("价格合计").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].remark").description("备注").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].status").description("状态：制单，已发布").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].createTime").description("创建时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].updateTime").description("更新时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].operator").description("修改人").type(JsonFieldType.NUMBER).optional()
                        )
                ));
    }

    @Test
    void findOne() throws Exception{
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/bill/find/{checkId}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/bill/find/one",
                        pathParameters(parameterWithName("checkId").description("传入对账单ID")),
                        responseFields(fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.OBJECT).optional(),
                                fieldWithPath("data.size").description("每页记录数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.currentPage").description("当前页码").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.totalPage").description("总页数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.totalElements").description("总记录数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data").description("数据列表").type(JsonFieldType.OBJECT).optional(),
                                fieldWithPath("data.checkId").description("对账单ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.checkCode").description("对账单编号").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.years").description("年").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.months").description("月").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.sumPrice").description("价格合计").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.remark").description("备注").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.status").description("状态：制单，已发布").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.createTime").description("创建时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.updateTime").description("更新时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.operator").description("修改人").type(JsonFieldType.NUMBER).optional()
                        )
                ));
    }

    @Test
    void findLine() throws Exception{
        this.mockMvc.perform(get("/bill/findLine").param("id","3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/bill/findLine",
                        requestParameters(parameterWithName("id").description("传入对账单ID").optional(),
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
                                fieldWithPath("data.list[].checkLineId").description("对账单行ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[].checkId").description("对账单ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[].orderCode").description("订单编号").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].rowNum").description("订单行号").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[].unit").description("单位").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].orderType").description("订单类型：送货单，退货单").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].productCode").description("成品编号").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].productName").description("成品名称").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].customerCode").description("客户编号").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].customerName").description("客户名称").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].actualQuantity").description("实际数量").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[].finishPrice").description("完结价格").type("float").optional(),
                                fieldWithPath("data.list[].sumPrice").description("行汇总价").type("float").optional(),
                                fieldWithPath("data.list[].finishDate").description("完结时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].remark").description("备注，预留").type(JsonFieldType.STRING).optional()
                        )
                ));
    }

    @Test
    void create() throws Exception{
        this.mockMvc.perform(get("/bill/create").param("ym","202102"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/bill/create",
                        requestParameters(parameterWithName("ym").description("传入年月，如：202102").optional()),
                        responseFields(fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据,对账单id").type(JsonFieldType.NUMBER).optional()
                        )
                ));

    }

    @Test
    void publish() throws Exception{

        this.mockMvc.perform(get("/bill/publish").param("id","3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/bill/publish",
                        requestParameters(parameterWithName("id").description("传入对账单ID").optional()),
                        responseFields(fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.OBJECT).optional()
                        )
                ));
    }

    @Test
    void delete() throws Exception{
        this.mockMvc.perform(get("/bill/delete").param("id","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/bill/delete",
                        requestParameters(parameterWithName("id").description("传入对账单ID").optional()),
                        responseFields(fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.OBJECT).optional()
                        )
                ));
    }

    @Test
    void toExcel() throws Exception{
        this.mockMvc.perform(get("/bill/delete").param("id","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/bill/delete",
                        requestParameters(parameterWithName("id").description("传入对账单ID").optional()),
                        responseFields(fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.OBJECT).optional()
                        )
                ));
    }

    @Test
    void save() throws Exception{

        this.mockMvc.perform(get("/bill/save").param("id","2").param("remark","测试"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/bill/save",
                        requestParameters(parameterWithName("id").description("传入对账单ID").optional(),
                                parameterWithName("remark").description("备注，目前只考虑header修改备注")),
                        responseFields(fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.OBJECT).optional()
                        )
                ));

    }
}