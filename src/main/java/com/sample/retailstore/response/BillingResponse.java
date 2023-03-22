package com.sample.retailstore.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.retailstore.domain.BillingObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author irfan.nagoo
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BillingResponse extends BaseResponse {

    private BillingObject billing;

    public BillingResponse(String status, String message) {
        super(status, message);
    }
}
