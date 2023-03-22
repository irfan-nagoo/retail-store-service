package com.sample.retailstore.request;

import com.sample.retailstore.constants.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author irfan.nagoo
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateOrderRequest {

    @NotNull
    private Long orderId;
    @NotNull
    private StatusType status;
    @NotNull
    private String updatedBy;

}
