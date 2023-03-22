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
public enum CategoryType {

    ELECTRONICS("Electronics"),
    GROCERY("Grocery"),
    MOBILE_PHONE("Mobile Phone"),
    TELEVISION("Television"),
    WASHING_MACHINE("Washing Machine"),
    RICE("Rice"),
    WHEAT("Wheat");

    @JsonValue
    private final String value;

    public static CategoryType getCategoryByValue(String value) {
        return Arrays.stream(CategoryType.values())
                .filter(subType -> subType.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

}
