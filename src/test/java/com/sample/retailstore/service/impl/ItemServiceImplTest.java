package com.sample.retailstore.service.impl;

import com.sample.retailstore.cache.CategoryCache;
import com.sample.retailstore.constants.CategoryType;
import com.sample.retailstore.domain.ItemObject;
import com.sample.retailstore.entity.Category;
import com.sample.retailstore.entity.Item;
import com.sample.retailstore.exception.RecordNotFoundException;
import com.sample.retailstore.mapper.ItemMapperImpl;
import com.sample.retailstore.repository.ItemRepository;
import com.sample.retailstore.request.ItemRequest;
import com.sample.retailstore.response.ItemResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapperImpl itemMapper;

    @Mock
    private CategoryCache categoryCache;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void getItemList() {
        ItemObject itemObject = new ItemObject();
        itemObject.setName("TestUser");
        when(itemRepository.findAll(any(PageRequest.class))).thenReturn(
                new PageImpl<Item>(Collections.singletonList(new Item())));
        when(itemMapper.itemToItemObject(any())).thenReturn(itemObject);
        ItemResponse response = itemService.getItemList(0, 1, "name", "asc");
        assertEquals(1, response.getItems().size());
        assertEquals("TestUser", response.getItems().get(0).getName());
    }

    @Test
    void getItemById_Success() {
        ItemObject itemObject = new ItemObject();
        itemObject.setName("TestUser");
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(new Item()));
        when(itemMapper.itemToItemObject(any())).thenReturn(itemObject);
        ItemResponse response = itemService.getItemById(1L);
        assertEquals(1, response.getItems().size());
        assertEquals("TestUser", response.getItems().get(0).getName());
    }

    @Test
    void getUserById_Exception() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> itemService.getItemById(1L));
        assertEquals("Item Not Found", exception.getMessage());
    }

    @Test
    void getItemByCategory_Success() {
        ItemObject itemObject = new ItemObject();
        itemObject.setName("TestUser");
        when(categoryCache.getSubCategoryList(any())).thenReturn(
                Collections.singletonList(new Category(1L, CategoryType.MOBILE_PHONE.getValue(), 10L)));
        when(itemRepository.findByCategoryIn(any())).thenReturn(Collections.singletonList(new Item()));
        when(itemMapper.itemToItemObject(any())).thenReturn(itemObject);
        ItemResponse response = itemService.getItemByCategory(CategoryType.MOBILE_PHONE);
        assertEquals(1, response.getItems().size());
        assertEquals("TestUser", response.getItems().get(0).getName());
    }

    @Test
    void getUserByCategory_Exception() {
        when(categoryCache.getSubCategoryList(any())).thenReturn(Collections.emptyList());
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> itemService.getItemByCategory(CategoryType.GROCERY));
        assertEquals("Item Not Found", exception.getMessage());
    }

    @Test
    void saveUser_Success() {
        ItemObject itemObject = new ItemObject();
        itemObject.setName("TestUser");
        Item item = new Item();
        when(itemMapper.itemObjectToItem(any())).thenReturn(item);
        when(itemMapper.itemToItemObject(any())).thenReturn(itemObject);
        when(itemRepository.save(any())).thenReturn(item);
        ItemResponse response = itemService.saveItem(new ItemRequest(itemObject));
        assertEquals(1, response.getItems().size());
        assertEquals("TestUser", response.getItems().get(0).getName());
    }

}