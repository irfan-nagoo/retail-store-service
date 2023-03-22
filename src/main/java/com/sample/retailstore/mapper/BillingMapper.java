package com.sample.retailstore.mapper;

import com.sample.retailstore.cache.StatusCache;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.domain.BillingObject;
import com.sample.retailstore.entity.Billing;
import com.sample.retailstore.entity.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author irfan.nagoo
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BillingMapper {

    @Autowired
    private StatusCache statusCache;

    @Mapping(target = "status", qualifiedByName = "StatusToBillingStatusType")
    public abstract BillingObject billingToBillingObject(Billing billing);

    @Mapping(target = "status", qualifiedByName = "BillingStatusTypeToStatus")
    @Mapping(target = "order", ignore = true)
    public abstract Billing billingObjectToBilling(BillingObject billingObject);

    @Named("StatusToBillingStatusType")
    protected StatusType mapToStatusType(Status status) {
        return StatusType.getStatusByValue(status.getValue());
    }

    @Named("BillingStatusTypeToStatus")
    protected Status mapToStatus(StatusType statusType) {
        return statusCache.getStatus("BILLING_STATUS", statusType.getValue());
    }

}
