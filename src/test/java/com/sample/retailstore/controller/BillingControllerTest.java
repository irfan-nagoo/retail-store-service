package com.sample.retailstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.retailstore.constants.CategoryType;
import com.sample.retailstore.constants.ItemType;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.domain.ItemObject;
import com.sample.retailstore.domain.OrderDetailObject;
import com.sample.retailstore.request.BillingRequest;
import com.sample.retailstore.response.BillingResponse;
import com.sample.retailstore.service.BillingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BillingController.class)
class BillingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillingService billingService;

    @Test
    void generateBill_Success() throws Exception {
        when(billingService.generateBill(any())).thenReturn
                (new BillingResponse(HttpStatus.OK.name(), "Some message"));
        BillingRequest request = new BillingRequest(getOrderDetails());
        String billingJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(post("/billing/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(billingJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    private List<OrderDetailObject> getOrderDetails() {
        OrderDetailObject orderDetailObject = new OrderDetailObject();
        ItemObject itemObject = new ItemObject();
        itemObject.setName("Sample Item1");
        itemObject.setCategory(Collections.singletonList(CategoryType.MOBILE_PHONE));
        itemObject.setType(ItemType.PACKED);
        itemObject.setStatus(StatusType.ITEM_AVAILABLE);
        itemObject.setBarcode("12345");
        itemObject.setQuantity(3L);
        itemObject.setPrice(BigDecimal.TEN);
        itemObject.setCreatedBy("TestUser");
        itemObject.setUpdatedBy("TestUser");
        orderDetailObject.setItem(itemObject);
        return Collections.singletonList(orderDetailObject);
    }

}