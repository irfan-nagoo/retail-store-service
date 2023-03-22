package com.sample.retailstore.domain;

import com.sample.retailstore.constants.PaymentType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * @author irfan.nagoo
 */

@Getter
public class PaymentObject {

    @NotNull
    private PaymentType type;

    public PaymentObject(PaymentType type) {
        this.type = type;
    }

}
