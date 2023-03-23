package com.sample.retailstore.cache;

import com.sample.retailstore.entity.Status;
import com.sample.retailstore.repository.StatusRepository;
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
class StatusCacheTest {


    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private StatusCache statusCache;

    @BeforeEach
    void setUp() {
        when(statusRepository.findAll()).thenReturn(
                Collections.singleton(new Status(1L, "ITEM_STATUS", "Available")));
        ReflectionTestUtils.invokeMethod(statusCache, "load");
    }

    @Test
    void getStatus() {
        Status type = statusCache.getStatus("ITEM_STATUS", "Available");
        assertEquals("Available", type.getValue());
    }
}