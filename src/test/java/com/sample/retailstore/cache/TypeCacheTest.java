package com.sample.retailstore.cache;

import com.sample.retailstore.entity.Type;
import com.sample.retailstore.repository.TypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TypeCacheTest {


    @Mock
    private TypeRepository typeRepository;

    @InjectMocks
    private TypeCache typeCache;

    @BeforeEach
    void setUp() {
        when(typeRepository.findAll()).thenReturn(
                Collections.singleton(new Type(1L, "ITEM_TYPE", "Packed")));
        ReflectionTestUtils.invokeMethod(typeCache, "load");
    }

    @Test
    void getType() {
        Type type = typeCache.getType("ITEM_TYPE", "Packed");
        assertEquals("Packed", type.getValue());
    }
}