package com.sample.retailstore.request;

import com.sample.retailstore.domain.UserObject;
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
public class UserRequest {

    @NotNull
    @Valid
    private UserObject user;

}
