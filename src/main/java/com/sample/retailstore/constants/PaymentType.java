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
public enum PaymentType {

    CARD("Card"),
    NET_BANKING("Net Banking"),
    UPI("UPI");

    @JsonValue
    private final String value;

    public static StatusType getStatusByValue(String value) {
        return Arrays.stream(StatusType.values())
                .filter(status -> status.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

}
