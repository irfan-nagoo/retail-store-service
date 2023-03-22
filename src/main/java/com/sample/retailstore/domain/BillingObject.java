package com.sample.retailstore.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.deserializer.CurrencyDeserializer;
import com.sample.retailstore.serializer.CurrencySerializer;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author irfan.nagoo
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillingObject {

    private Long id;
    private String transId;
    private StatusType status;
    @JsonSerialize(using = CurrencySerializer.class)
    @JsonDeserialize(using = CurrencyDeserializer.class)
    private BigDecimal shippingCharges;
    private BigDecimal discount;
    private BigDecimal tax;
    @JsonSerialize(using = CurrencySerializer.class)
    @JsonDeserialize(using = CurrencyDeserializer.class)
    private BigDecimal totalCharged;
}
