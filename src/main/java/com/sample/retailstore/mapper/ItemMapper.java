package com.sample.retailstore.mapper;

import com.sample.retailstore.cache.CategoryCache;
import com.sample.retailstore.cache.StatusCache;
import com.sample.retailstore.cache.TypeCache;
import com.sample.retailstore.constants.CategoryType;
import com.sample.retailstore.constants.ItemType;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.constants.UnitType;
import com.sample.retailstore.domain.ItemObject;
import com.sample.retailstore.entity.Category;
import com.sample.retailstore.entity.Item;
import com.sample.retailstore.entity.Status;
import com.sample.retailstore.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author irfan.nagoo
 */


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ItemMapper {

    @Autowired
    private CategoryCache categoryCache;
    @Autowired
    private TypeCache typeCache;
    @Autowired
    private StatusCache statusCache;

    @Mapping(target = "category", qualifiedByName = "CategoryToCategoryList")
    @Mapping(target = "type", qualifiedByName = "TypeToItemType")
    @Mapping(target = "status", qualifiedByName = "StatusToStatusType")
    @Mapping(target = "unit", qualifiedByName = "StringToUnitType")
    public abstract ItemObject itemToItemObject(Item item);

    @Mapping(target = "category", qualifiedByName = "CategoryListToCategory")
    @Mapping(target = "type", qualifiedByName = "ItemTypeToType")
    @Mapping(target = "status", qualifiedByName = "StatusTypeToStatus")
    @Mapping(target = "unit", qualifiedByName = "UnitTypeToString")
    @Mapping(target = "version", defaultValue = "1l")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
    public abstract Item itemObjectToItem(ItemObject itemObject);

    @Named("CategoryToCategoryList")
    protected List<CategoryType> mapToCategoryList(Category category) {
        return categoryCache.getSuperCategoryTypeList(category);
    }

    @Named("TypeToItemType")
    protected ItemType mapToItemType(Type type) {
        return ItemType.getTypeByValue(type.getValue());
    }

    @Named("StatusToStatusType")
    protected StatusType mapToStatusType(Status status) {
        return StatusType.getStatusByValue(status.getValue());
    }

    @Named("StringToUnitType")
    protected UnitType mapToUnitType(String unit) {
        return UnitType.getUnitByValue(unit);
    }

    @Named("CategoryListToCategory")
    protected Category mapToCategory(List<CategoryType> category) {
        if (!CollectionUtils.isEmpty(category)) {
            return categoryCache.getCategory(category.get(0).getValue());
        }
        return null;
    }

    @Named("ItemTypeToType")
    protected Type mapToStatus(ItemType itemType) {
        return typeCache.getType("ITEM_TYPE", itemType.getValue());
    }

    @Named("StatusTypeToStatus")
    protected Status mapToStatus(StatusType statusType) {
        return statusCache.getStatus("ITEM_STATUS", statusType.getValue());
    }

    @Named("UnitTypeToString")
    protected String mapToUnit(UnitType unit) {
        return null != unit ? unit.getValue() : null;
    }
}
