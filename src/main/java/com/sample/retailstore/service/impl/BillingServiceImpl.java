package com.sample.retailstore.service.impl;

import com.sample.retailstore.domain.BillingObject;
import com.sample.retailstore.domain.ItemObject;
import com.sample.retailstore.domain.OrderDetailObject;
import com.sample.retailstore.request.BillingRequest;
import com.sample.retailstore.response.BillingResponse;
import com.sample.retailstore.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.sample.retailstore.constants.MessageConstants.GET_SUCCESS_MSG;
import static com.sample.retailstore.constants.MessageConstants.ORDER_INVALID_QUANTITY_MSG;
import static com.sample.retailstore.constants.RetailStoreConstants.*;

/**
 * @author irfan.nagoo
 */

@Service
@Transactional
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    @Override
    public BillingResponse generateBill(BillingRequest request) {
        BillingObject billing = new BillingObject();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderDetailObject od : request.getOrderDetails()) {
            ItemObject itemObject = od.getItem();
            long orderedQty = od.getQuantity();
            long itemQty = itemObject.getQuantity();
            totalAmount = totalAmount.add(itemObject.getPrice().multiply(BigDecimal.valueOf(orderedQty)));
            if (orderedQty > itemQty) {
                throw new IllegalArgumentException(String.format(ORDER_INVALID_QUANTITY_MSG, orderedQty, itemQty));
            }
        }
        if (DISCOUNT_PERCENT > 0) {
            BigDecimal discount = totalAmount.multiply(BigDecimal.valueOf(DISCOUNT_PERCENT))
                    .divide(BigDecimal.valueOf(100));
            totalAmount = totalAmount.subtract(discount);
            billing.setDiscount(discount);
        }
        if (TAX_CHARGED_PERCENT > 0) {
            BigDecimal taxCharged = totalAmount.multiply(BigDecimal.valueOf(TAX_CHARGED_PERCENT))
                    .divide(BigDecimal.valueOf(100));
            totalAmount = totalAmount.add(taxCharged);
            billing.setTax(taxCharged);
        }
        BigDecimal shippingCharges = BigDecimal.valueOf(SHIPPING_CHARGES);
        totalAmount = totalAmount.add(shippingCharges);
        billing.setShippingCharges(shippingCharges);
        billing.setTotalCharged(totalAmount);

        BillingResponse response = new BillingResponse(HttpStatus.OK.name(), GET_SUCCESS_MSG);
        response.setBilling(billing);
        return response;
    }
}
