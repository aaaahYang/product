package com.product.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class RestDocsControllerTest {

    MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider provider){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .build();
    }
}
