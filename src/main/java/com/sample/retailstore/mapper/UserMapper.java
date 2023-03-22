package com.sample.retailstore.mapper;

import com.sample.retailstore.cache.StatusCache;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.domain.UserObject;
import com.sample.retailstore.entity.Status;
import com.sample.retailstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author irfan.nagoo
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    @Autowired
    private StatusCache statusCache;

    @Mapping(target = "status", qualifiedByName = "StatusToStatusType")
    public abstract UserObject userToUserObject(User user);

    @Mapping(target = "status", qualifiedByName = "StatusTypeToStatus")
    @Mapping(target = "version", defaultValue = "1l")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
    public abstract User userObjectToUser(UserObject user);

    @Named("StatusToStatusType")
    protected StatusType mapToStatusType(Status status) {
        return StatusType.getStatusByValue(status.getValue());
    }

    @Named("StatusTypeToStatus")
    protected Status mapToStatus(StatusType statusType) {
        return statusCache.getStatus("USER_STATUS", statusType.getValue());
    }

}
