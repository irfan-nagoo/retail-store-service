package com.sample.retailstore.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sample.retailstore.constants.CategoryType;
import com.sample.retailstore.constants.ItemType;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.constants.UnitType;
import com.sample.retailstore.deserializer.CurrencyDeserializer;
import com.sample.retailstore.serializer.CurrencySerializer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author irfan.nagoo
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemObject {

    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private List<CategoryType> category;
    @NotNull
    private ItemType type;
    private String manufacturer;
    @NotEmpty
    private String barcode;
    @NotNull
    private StatusType status;
    @NotNull
    @JsonSerialize(using = CurrencySerializer.class)
    @JsonDeserialize(using = CurrencyDeserializer.class)
    private BigDecimal price;
    private Long quantity;
    private UnitType unit;
    private Long version;
    private LocalDateTime createDate;
    @NotEmpty
    private String createdBy;
    private LocalDateTime updateDate;
    @NotEmpty
    private String updatedBy;
}
