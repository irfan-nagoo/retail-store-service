package com.sample.retailstore.constants;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * @author irfan.nagoo
 */

@Getter
@RequiredArgsConstructor
public enum StatusType {

    // Item status
    ITEM_AVAILABLE("Available"),
    ITEM_OUT_OF_STOCK("Out Of Stock"),

    // User status
    USER_ACTIVE("Active"),
    USER_OUT_OF_STOCK("Not Active"),

    // Order status
    ORDER_PLACED("Placed"),
    ORDER_SHIPPED("Shipped"),
    ORDER_IN_TRANSIT("In Transit"),
    ORDER_DELIVERED("Delivered"),
    ORDER_CANCELLED("Cancelled"),

    // Billing status
    BILLING_PAID("Paid"),
    BILLING_FAILED("Failed");


    @JsonValue
    private final String value;

    public static StatusType getStatusByValue(String value) {
        return Arrays.stream(StatusType.values())
                .filter(status -> status.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }
}
