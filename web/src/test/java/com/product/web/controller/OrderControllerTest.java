package com.product.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.product.entity.OrderLine;
import com.product.entity.form.OrderForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
class OrderControllerTest extends RestDocsControllerTest{

    @Test
    void findList() throws Exception {

        this.mockMvc.perform(post("/order/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("order/list",
                        requestParameters(parameterWithName("param").description("模糊查询使用，传入order对象json字符串,如{orderType:value,orderNum:value},界面显示搜索字段：订单类型，订单编码，状态，客户编码，客户名称，创建时间，完结时间").optional(),
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
                                fieldWithPath("data.list[].orderId").description("订单ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[].orderType").description("订单类型").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].orderNum").description("订单编码").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].status").description("状态").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].customerCode").description("客户编码").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].customerName").description("客户名称").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].deliveryAddress").description("送货地址").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].sendAddress").description("发货地址").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].sumPrice").description("总价").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.list[].remark").description("备注").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].phoneNum").description("联系电话").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].finishTime").description("完结时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].createTime").description("创建时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].updateTime").description("更新时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.list[].operator").description("修改人").type(JsonFieldType.NUMBER).optional()
                        )
                ));
    }

    @Test
    void findOne() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/order/find/{orderId}",5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/order/find/one",
                        pathParameters(parameterWithName("orderId").description("传入订单ID")),
                        responseFields(
                                fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.OBJECT).optional(),
                                fieldWithPath("data.size").description("每页记录数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.currentPage").description("当前页码").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.totalPage").description("总页数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.totalElements").description("总记录数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.orderId").description("订单ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.orderType").description("订单类型").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.orderNum").description("订单编号").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.status").description("状态").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.customerCode").description("客户编号").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.customerName").description("客户名称").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.deliveryAddress").description("送货地址").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.sendAddress").description("发货地址").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.sumPrice").description("总价").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.remark").description("备注").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.phoneNum").description("联系电话").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.finishTime").description("完结时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.createTime").description("创建时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.updateTime").description("更新时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.operator").description("修改人").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.orderLines[].orderLineId").description("订单行ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.orderLines[].orderId").description("订单ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.orderLines[].productCode").description("成品编号").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.orderLines[].productName").description("成品名称").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.orderLines[].unit").description("单位").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.orderLines[].quantity").description("订单数量").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.orderLines[].actualQuantity").description("实际数量").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.orderLines[].defaultPrice").description("默认单价").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.orderLines[].finishPrice").description("实际单价").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.orderLines[].remark").description("备注").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.orderLines[].createTime").description("创建时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.orderLines[].updateTime").description("更新时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.orderLines[].operator").description("修改人").type(JsonFieldType.NUMBER).optional()
                        )
                ));
    }

    @Test
    void save() throws Exception {

        OrderForm orderForm = new OrderForm();
        orderForm.setOrderId(5);
        orderForm.setOrderNum("SH202102010001");
        orderForm.setAction("finish");
        orderForm.setOrderType("送货单");
        orderForm.setStatus("完结");
        orderForm.setCustomerCode("dj-1");
        orderForm.setCustomerName("电机1");
        List list = new ArrayList();
        OrderLine orderLine = new OrderLine();
        orderLine.setOrderLineId(3);
        orderLine.setOrderId(5);
        orderLine.setProductCode("ceshi");
        orderLine.setProductName("123");
        orderLine.setQuantity(2);
        orderLine.setDefaultPrice(new BigDecimal("0.101"));
        list.add(orderLine);
        orderForm.setOrderLines(list);

        String jsonStr = JSONObject.toJSONString(orderForm);
        this.mockMvc.perform(post("/order/save").content(jsonStr.getBytes()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/order/save",
                        requestFields(
                                fieldWithPath("action").description("执行操作，save：保存，finish：完结").type(JsonFieldType.STRING),
                                fieldWithPath("orderId").description("订单ID，创建时后台生成,修改时必填").type("int").optional(),
                                fieldWithPath("orderNum").description("订单编号，创建时后台生成,修改时必填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("orderType").description("订单类型，暂有类型：送货单，退货单，必填").type(JsonFieldType.STRING),
                                fieldWithPath("status").description("状态：制单，完结，必填，不可修改").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("customerCode").description("客户编码，必填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("customerName").description("客户名称，必填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("deliveryAddress").description("送货地址，选填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("sendAddress").description("发货地址，选填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("sumPrice").description("总价，选填，不可修改").type("float").optional(),
                                fieldWithPath("remark").description("备注，选填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("phoneNum").description("联系电话，选填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("orderLines[]").description("订单明细列表").type(JsonFieldType.ARRAY).optional(),
                                fieldWithPath("orderLines[].orderLineId").description("订单明细ID，创建是后台生成，修改时必填").type("int").optional(),
                                fieldWithPath("orderLines[].orderId").description("客户ID，与头表ID一致，必填").type("int").optional(),
                                fieldWithPath("orderLines[].productCode").description("成品编号，取成品列表值，必填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("orderLines[].productName").description("成品名称，选择成品带入，必填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("orderLines[].unit").description("单位，选择成品带入，不能修改").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("orderLines[].quantity").description("订单数量，必填").type("int").optional(),
                                fieldWithPath("orderLines[].actualQuantity").description("实际数量，完结时必填").type("int").optional(),
                                fieldWithPath("orderLines[].defaultPrice").description("默认价格，选择成品带入，必填").type("float").optional(),
                                fieldWithPath("orderLines[].finishPrice").description("完结价格，完结时必填").type("float").optional(),
                                fieldWithPath("orderLines[].remark").description("备注，选填").type(JsonFieldType.STRING).optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.NULL).optional()
                        )


                ));



    }

    @Test
    void delete() throws Exception {
        this.mockMvc.perform(post("/order/delete").param("orderId","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(MockMvcRestDocumentation.document("/order/delete",
                        requestParameters(parameterWithName("orderId").description("订单ID,传入int")),
                        responseFields(
                                fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.NULL).optional()
                        )
                ));
    }

    @Test
    void deleteLine() throws Exception {

        this.mockMvc.perform(post("/order/deleteLine").param("orderLineIds","1,2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/order/deleteLine",
                        requestParameters(parameterWithName("orderLineIds").description("订单明细ID，传入int[]")),
                        responseFields(
                                fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.NULL).optional()
                        )
                ));

    }
}