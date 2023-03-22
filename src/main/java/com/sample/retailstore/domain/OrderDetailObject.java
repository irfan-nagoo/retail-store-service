package com.sample.retailstore.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author irfan.nagoo
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailObject {

    private Long id;
    @NotNull
    @Valid
    private ItemObject item;
    private Long quantity;
}
