package com.sample.retailstore.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.retailstore.constants.StatusType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author irfan.nagoo
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderObject {

    private Long id;
    private String orderNumber;
    private StatusType status;
    @NotNull
    private UserObject user;
    @NotNull
    private List<OrderDetailObject> orderDetails;
    @NotNull
    private BillingObject billing;
    private Long version;
    private LocalDateTime createDate;
    @NotEmpty
    private String createdBy;
    private LocalDateTime updateDate;
    @NotEmpty
    private String updatedBy;

}
