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
public enum ItemType {

    PACKED("Packed"),
    LOOSE("Loose");

    @JsonValue
    private final String value;

    public static ItemType getTypeByValue(String value) {
        return Arrays.stream(ItemType.values())
                .filter(type -> type.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

}
