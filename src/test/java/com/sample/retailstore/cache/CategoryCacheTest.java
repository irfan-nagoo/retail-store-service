package com.sample.retailstore.cache;

import com.sample.retailstore.constants.CategoryType;
import com.sample.retailstore.entity.Category;
import com.sample.retailstore.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryCacheTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryCache categoryCache;

    @BeforeEach
    void setUp() {
        Category category = new Category(1L, "Grocery", null);
        Category subCategory = new Category(2L, "Rice", 1L);
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category, subCategory));
        ReflectionTestUtils.invokeMethod(categoryCache, "load");
    }

    @Test
    void getCategory() {
        Category category = categoryCache.getCategory("Grocery");
        assertEquals("Grocery", category.getCategory());
    }

    @Test
    void getSuperCategoryTypeList() {
        Category category = new Category(1L, "Grocery", null);
        List<CategoryType> categoryList = categoryCache.getSuperCategoryTypeList(category);
        assertEquals(1, categoryList.size());
        assertEquals("Grocery", categoryList.get(0).getValue());
    }

    @Test
    void getSubCategoryList() {
        List<Category> categoryList = categoryCache.getSubCategoryList(CategoryType.GROCERY);
        assertEquals(2, categoryList.size());
        assertEquals("Rice", categoryList.get(1).getCategory());
    }

}