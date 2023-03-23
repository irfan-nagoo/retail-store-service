package com.sample.retailstore.service.impl;

import com.sample.retailstore.domain.ItemObject;
import com.sample.retailstore.domain.OrderDetailObject;
import com.sample.retailstore.request.BillingRequest;
import com.sample.retailstore.response.BillingResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static com.sample.retailstore.constants.MessageConstants.ORDER_INVALID_QUANTITY_MSG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class BillingServiceImplTest {

    @InjectMocks
    private BillingServiceImpl billingService;

    @Test
    void generateBill_Success() {
        OrderDetailObject orderDetailObject1 = new OrderDetailObject();
        ItemObject itemObject1 = new ItemObject();
        itemObject1.setName("Sample Item1");
        itemObject1.setQuantity(3L);
        itemObject1.setPrice(BigDecimal.TEN);
        orderDetailObject1.setItem(itemObject1);
        orderDetailObject1.setQuantity(1L);
        OrderDetailObject orderDetailObject2 = new OrderDetailObject();
        ItemObject itemObject2 = new ItemObject();
        itemObject2.setName("Sample Item2");
        itemObject2.setQuantity(2L);
        itemObject2.setPrice(BigDecimal.TEN);
        orderDetailObject2.setItem(itemObject2);
        orderDetailObject2.setQuantity(2L);
        BillingRequest request = new BillingRequest(Arrays.asList(orderDetailObject1, orderDetailObject2));
        BillingResponse response = billingService.generateBill(request);
        assertEquals(BigDecimal.valueOf(80.0), response.getBilling().getTotalCharged());
        assertEquals(BigDecimal.valueOf(50.0), response.getBilling().getShippingCharges());
    }

    @Test
    void generateBill_Exception() {
        OrderDetailObject orderDetailObject1 = new OrderDetailObject();
        ItemObject itemObject1 = new ItemObject();
        itemObject1.setName("Sample Item1");
        itemObject1.setQuantity(1L);
        itemObject1.setPrice(BigDecimal.TEN);
        orderDetailObject1.setItem(itemObject1);
        orderDetailObject1.setQuantity(2L);
        BillingRequest request = new BillingRequest(Collections.singletonList(orderDetailObject1));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> billingService.generateBill(request));
        assertEquals(String.format(ORDER_INVALID_QUANTITY_MSG, orderDetailObject1.getQuantity(), itemObject1.getQuantity()), exception.getMessage());
    }
}