package com.sample.retailstore.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.retailstore.constants.StatusType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author irfan.nagoo
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserObject {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotEmpty
    private String email;
    @NotEmpty
    private String mobile;
    @NotNull
    private StatusType status;
    private Long version;
    private LocalDateTime createDate;
    @NotEmpty
    private String createdBy;
    private LocalDateTime updateDate;
    @NotEmpty
    private String updatedBy;
}
