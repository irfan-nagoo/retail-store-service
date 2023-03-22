package com.sample.retailstore.service;

import com.sample.retailstore.constants.CategoryType;
import com.sample.retailstore.request.ItemRequest;
import com.sample.retailstore.response.ItemResponse;

/**
 * @author irfan.nagoo
 */
public interface ItemService {

    /**
     * Paginated and sorted list of Items
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortDirection
     * @return
     */
    ItemResponse getItemList(int pageNo, int pageSize, String sortBy, String sortDirection);

    /**
     * Get Item by id
     *
     * @param id
     * @return
     */
    ItemResponse getItemById(Long id);

    /**
     * Get Item by Category
     *
     * @param category
     * @return
     */
    ItemResponse getItemByCategory(CategoryType category);

    /**
     * Save or Update Item record
     *
     * @param request
     * @return
     */
    ItemResponse saveItem(ItemRequest request);

}
