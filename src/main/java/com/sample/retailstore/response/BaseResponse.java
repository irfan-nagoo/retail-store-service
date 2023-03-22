package com.sample.retailstore.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author irfan.nagoo
 */

@AllArgsConstructor
@Getter
public class BaseResponse {

    private final String status;
    private final String message;
}
