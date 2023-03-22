package com.sample.retailstore.request;

import com.sample.retailstore.domain.OrderDetailObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author irfan.nagoo
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BillingRequest {

    @NotNull
    @Valid
    private List<OrderDetailObject> orderDetails;

}
