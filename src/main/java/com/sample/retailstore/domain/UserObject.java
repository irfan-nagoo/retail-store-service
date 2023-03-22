package com.sample.retailstore.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.retailstore.constants.StatusType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author irfan.nagoo
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserObject {

    private Long id;
    private String name;
    private String address;
    private String email;
    private String mobile;
    private StatusType status;
    private Long version;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime updateDate;
    private String updatedBy;
}
