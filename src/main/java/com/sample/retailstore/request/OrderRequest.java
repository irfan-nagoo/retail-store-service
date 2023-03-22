package com.sample.retailstore.request;

import com.sample.retailstore.domain.OrderObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author irfan.nagoo
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderRequest {

    @NotNull
    @Valid
    private OrderObject order;

}
