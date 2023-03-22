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
public enum UnitType {

    KILO_GRAM("kg"),
    GRAM("g"),
    LITRE("l"),
    MILLI_LITRE("ml"),
    NONE("");

    @JsonValue
    private final String value;

    public static UnitType getUnitByValue(String value) {
        return Arrays.stream(UnitType.values())
                .filter(unit -> unit.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }
}
