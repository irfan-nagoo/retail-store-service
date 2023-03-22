package com.sample.retailstore.service;

import com.sample.retailstore.request.BillingRequest;
import com.sample.retailstore.response.BillingResponse;

/**
 * @author irfan.nagoo
 */
public interface BillingService {

    /**
     * Get bill information based on the list of selected items
     *
     * @param request
     * @return
     */
    BillingResponse generateBill(BillingRequest request);
}
