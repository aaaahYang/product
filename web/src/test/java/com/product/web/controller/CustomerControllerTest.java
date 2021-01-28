package com.product.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.product.entity.CustomerProduct;
import com.product.entity.form.CustomerForm;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


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

        this.mockMvc.perform(get("/customer/list?customerCode=dj"))
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

    @Test
    void findOne() throws Exception{
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/customer/find/{customerId}",5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/customer/find/one",
                        pathParameters(parameterWithName("customerId").description("传入客户ID")),
                        responseFields(
                                fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.OBJECT).optional(),
                                fieldWithPath("data.size").description("每页记录数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.currentPage").description("当前页码").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.totalPage").description("总页数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.totalElements").description("总记录数").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.customerId").description("客户ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.customerName").description("客户名称").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.customerCode").description("客户编码").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.phoneNum").description("联系电话").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.address").description("地址").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.remark").description("备注").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.createTime").description("创建时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.updateTime").description("更新时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.operator").description("修改人").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.customerProductDTOS[].productLineId").description("客户成品ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.customerProductDTOS[].customerId").description("客户ID").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.customerProductDTOS[].productCode").description("成品编号").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.customerProductDTOS[].materialCode").description("物料编码").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.customerProductDTOS[].price").description("默认价格").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.customerProductDTOS[].sizeDescription").description("开料尺寸").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.customerProductDTOS[].unit").description("单位").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.customerProductDTOS[].remark").description("备注").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.customerProductDTOS[].operator").description("修改人").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("data.customerProductDTOS[].createTime").description("创建时间").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data.customerProductDTOS[].updateTime").description("更新时间").type(JsonFieldType.STRING).optional()
                        )
                ));
    }

    @Test
    public void save() throws Exception{

        CustomerForm customerForm = new CustomerForm();
        customerForm.setCustomerId(6);
        customerForm.setCustomerCode("kh-003");
        customerForm.setCustomerName("客户002");
        CustomerProduct customerProduct = new CustomerProduct();
        customerProduct.setProductLineId(2);
        customerProduct.setProductCode("aab01");
        customerProduct.setCustomerId(6);
        customerProduct.setPrice(new BigDecimal(10.0));
        List<CustomerProduct> list = new ArrayList<>();
        list.add(customerProduct);
        customerForm.setCustomerProducts(list);

        String jsonStr = JSONObject.toJSONString(customerForm);

        this.mockMvc.perform(post("/customer/save").content(jsonStr.getBytes()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/customer/save",
                        requestFields(
                                fieldWithPath("customerId").description("客户ID，创建时后台生成").type("int").optional(),
                                fieldWithPath("customerCode").description("客户编码，创建后不可修改，必填").type(JsonFieldType.STRING),
                                fieldWithPath("customerName").description("客户名称，必填").type(JsonFieldType.STRING),
                                fieldWithPath("phoneNum").description("联系电话，预留，选填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("address").description("客户地址，预留，选填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("remark").description("备注，选填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("customerProducts[]").description("客户成品列表").type(JsonFieldType.ARRAY).optional(),
                                fieldWithPath("customerProducts[].productLineId").description("客户成品ID，创建是后台生成").type("int").optional(),
                                fieldWithPath("customerProducts[].customerId").description("客户ID，与头表ID一致，必填").type("int").optional(),
                                fieldWithPath("customerProducts[].productCode").description("成品编码，取成品列表值，必填").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("customerProducts[].price").description("默认价格，必填").type("float").optional(),
                                fieldWithPath("customerProducts[].remark").description("备注，选填").type(JsonFieldType.STRING).optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.NULL).optional()
                        )


                        ));
    }

    @Test
    public void delete () throws Exception {

        this.mockMvc.perform(post("/customer/delete").param("customerIds","1,2,3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/customer/delete",
                        requestParameters(parameterWithName("customerIds").description("客户ID,传入int[]")),
                        responseFields(
                                fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.NULL).optional()
                        )
                ));
    }

    @Test
    public void deleteLine() throws Exception {

        this.mockMvc.perform(post("/customer/deleteLine").param("productLineIds","1,2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/customer/deleteLine",
                        requestParameters(parameterWithName("productLineIds").description("客户成品ID，传入int[]")),
                        responseFields(
                                fieldWithPath("code").description("返回状态码，0表示成功").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("msg").description("返回信息").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("data").description("返回数据").type(JsonFieldType.NULL).optional()
                        )
                ));

    }





}