package com.sample.retailstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.domain.*;
import com.sample.retailstore.request.OrderRequest;
import com.sample.retailstore.request.UpdateOrderRequest;
import com.sample.retailstore.response.OrderResponse;
import com.sample.retailstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void getOrderList_Success() throws Exception {
        when(orderService.getOrderList()).thenReturn(new OrderResponse(HttpStatus.OK.name(), "Some message"));
        mockMvc.perform(get("/order/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    @Test
    void getOrderById_Success() throws Exception {
        when(orderService.getOrderById(anyLong())).thenReturn(new OrderResponse(HttpStatus.OK.name(), "Some message"));
        mockMvc.perform(get("/order/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    @Test
    void placeOrder_Success() throws Exception {
        when(orderService.placeOrder(any())).thenReturn(new OrderResponse(HttpStatus.OK.name(), "Some message"));
        OrderRequest request = new OrderRequest(getOrderObject());
        String orderJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(post("/order/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    @Test
    void cancelOrder_Success() throws Exception {
        when(orderService.cancelOrder(any())).thenReturn(new OrderResponse(HttpStatus.OK.name(), "Some message"));
        OrderRequest request = new OrderRequest(getOrderObject());
        String orderJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(put("/order/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    @Test
    void updateOrderStatus_Success() throws Exception {
        when(orderService.updateOrderStatus(any())).thenReturn(new OrderResponse(HttpStatus.OK.name(), "Some message"));
        UpdateOrderRequest request = new UpdateOrderRequest(10L, StatusType.ORDER_SHIPPED, "Test User");
        String orderJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(put("/order/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    private OrderObject getOrderObject() {
        OrderObject orderObject = new OrderObject();
        orderObject.setStatus(StatusType.ORDER_PLACED);
        orderObject.setOrderNumber("OR1234567");
        OrderDetailObject orderDetailObject = new OrderDetailObject();
        ItemObject itemObject = new ItemObject();
        itemObject.setName("Sample Item");
        itemObject.setQuantity(1L);
        itemObject.setPrice(BigDecimal.TEN);
        orderDetailObject.setItem(itemObject);
        orderDetailObject.setQuantity(1L);
        orderObject.setOrderDetails(Collections.singletonList(orderDetailObject));
        orderObject.setBilling(new BillingObject());
        orderObject.setUser(new UserObject());
        orderObject.setCreatedBy("Test User");
        orderObject.setUpdatedBy("Test User");
        return orderObject;
    }
}