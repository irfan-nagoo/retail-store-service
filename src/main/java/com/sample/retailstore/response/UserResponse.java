package com.sample.retailstore.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.retailstore.domain.UserObject;
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
public class UserResponse extends BaseResponse {
    private List<UserObject> users = new ArrayList<>();

    public UserResponse(String status, String message) {
        super(status, message);
    }
}
