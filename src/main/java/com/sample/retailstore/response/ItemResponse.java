package com.sample.retailstore.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.retailstore.domain.ItemObject;
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
public class ItemResponse extends BaseResponse {

    private List<ItemObject> items = new ArrayList<>();

    public ItemResponse(String status, String message) {
        super(status, message);
    }

}
