package com.sample.retailstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.retailstore.constants.CategoryType;
import com.sample.retailstore.constants.ItemType;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.domain.ItemObject;
import com.sample.retailstore.request.ItemRequest;
import com.sample.retailstore.response.ItemResponse;
import com.sample.retailstore.service.ItemService;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    void getItemList_Success() throws Exception {
        when(itemService.getItemList(anyInt(), anyInt(), anyString(), anyString())).thenReturn(
                new ItemResponse(HttpStatus.OK.name(), "Some message"));
        mockMvc.perform(get("/item/list")
                        .param("pageNo", "1")
                        .param("pageSize", "2")
                        .param("sortBy", "name")
                        .param("sortDirection", "asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    @Test
    void getItemById_Success() throws Exception {
        when(itemService.getItemById(anyLong())).thenReturn(new ItemResponse(HttpStatus.OK.name(), "Some message"));
        mockMvc.perform(get("/item/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    @Test
    void getItemByCategory_Success() throws Exception {
        when(itemService.getItemByCategory(any())).thenReturn(new ItemResponse(HttpStatus.OK.name(), "Some message"));
        mockMvc.perform(get("/item/category/Grocery"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    @Test
    void saveItem_Success() throws Exception {
        when(itemService.saveItem(any())).thenReturn(new ItemResponse(HttpStatus.OK.name(), "Some message"));
        ItemRequest request = new ItemRequest(getItemObject());
        String itemJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(post("/item/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    private ItemObject getItemObject() {
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
        return itemObject;
    }
}