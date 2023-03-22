package com.sample.retailstore.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.retailstore.domain.OrderObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author irfan.nagoo
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderResponse extends BaseResponse {

    private List<OrderObject> orders = new ArrayList<>();

    public OrderResponse(String status, String message) {
        super(status, message);
    }

}
