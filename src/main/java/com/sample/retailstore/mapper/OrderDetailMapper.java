package com.sample.retailstore.mapper;

import com.sample.retailstore.domain.OrderDetailObject;
import com.sample.retailstore.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author irfan.nagoo
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = ItemMapper.class)
public abstract class OrderDetailMapper {

    public abstract OrderDetailObject orderDetailToOrderDetailObject(OrderDetail orderDetail);

    @Mapping(target = "order", ignore = true)
    public abstract OrderDetail orderDetailObjectToOrderDetail(OrderDetailObject orderDetailObject);


}
