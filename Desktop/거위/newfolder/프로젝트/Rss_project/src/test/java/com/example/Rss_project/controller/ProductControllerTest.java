package com.example.Rss_project.controller;


import com.example.Rss_project.data.dto.ProductDTO;
import com.example.Rss_project.data.service.Impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.web.servlet.function.ServerResponse.status;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebMvcTest(ProductController.class)

public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    ProductServiceImpl productService;

    @Test
    @DisplayName("Product 데이터 가져오기 테스트")
    void getProductTest() throws Exception{
        

        given(productService.getProduct("23333")).willReturn(
                new ProductDTO("3687", "pen", 4500, 5500)
        );
        String productId = "23333";


        mockMvc.perform(
                get("/testproject/product-api/product/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").exists())
                .andExpect(jsonPath("$.productName").exists())
                .andExpect(jsonPath("$.productPrice").exists())
                .andExpect(jsonPath("$.productStock").exists())
                .andDo(print());


        verify(productService).getProduct("23333");
    }

    @Test
    @DisplayName("Product 데이터 생성 테스트")
    void createProductTest() throws  Exception{
        given(productService.saveProduct("3687","pen", 4500, 5500)).willReturn(
                new ProductDTO("3687", "pen", 4500, 5500));

        ProductDTO productDTO = ProductDTO.builder().productId("3687").productName("pen").productPrice(4500).productStock(5500).build();
        Gson gson = new Gson();
        String content = gson.toJson(productDTO);


        mockMvc.perform(
                post("/testproject/product-api/product")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.productId").exists())
                .andExpect(jsonPath("$.productName").exists())
                .andExpect(jsonPath("$.productPrice").exists())
                .andExpect(jsonPath("$.productStock").exists())
                .andDo(print());
        verify(productService).saveProduct("3687", "pen", 4500, 5500);

    }
}
