package com.sample.retailstore.controller;

import com.sample.retailstore.request.BillingRequest;
import com.sample.retailstore.response.BillingResponse;
import com.sample.retailstore.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author irfan.nagoo
 */

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    /**
     * Generate billing information based on selected item details
     *
     * @return
     */
    @PostMapping("/generate")
    public BillingResponse generateBill(@Valid @RequestBody BillingRequest request) {
        return billingService.generateBill(request);
    }
}
