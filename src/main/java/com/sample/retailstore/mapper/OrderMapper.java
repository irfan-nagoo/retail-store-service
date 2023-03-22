package com.sample.retailstore.mapper;

import com.sample.retailstore.cache.StatusCache;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.domain.OrderObject;
import com.sample.retailstore.entity.Order;
import com.sample.retailstore.entity.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author irfan.nagoo
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class, ItemMapper.class, OrderDetailMapper.class, BillingMapper.class})
public abstract class OrderMapper {

    @Autowired
    private StatusCache statusCache;

    @Mapping(target = "status", qualifiedByName = "StatusToOrderStatusType")
    public abstract OrderObject orderToOrderObject(Order order);

    @Mapping(target = "status", qualifiedByName = "OrderStatusTypeToStatus")
    @Mapping(target = "version", defaultValue = "1l")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
    public abstract Order orderObjectToOrder(OrderObject orderObject);

    @Named("StatusToOrderStatusType")
    protected StatusType mapToStatusType(Status status) {
        return StatusType.getStatusByValue(status.getValue());
    }

    @Named("OrderStatusTypeToStatus")
    protected Status mapToStatus(StatusType statusType) {
        return statusCache.getStatus("ORDER_STATUS", statusType.getValue());
    }

}
