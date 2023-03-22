package com.sample.retailstore.controller;

import com.sample.retailstore.constants.CategoryType;
import com.sample.retailstore.request.ItemRequest;
import com.sample.retailstore.response.ItemResponse;
import com.sample.retailstore.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author irfan.nagoo
 */
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * Get paginated and sorted list of Items
     *
     * @param pageNo        page number starting at 0
     * @param pageSize      page size
     * @param sortBy        comma separated sort fields
     * @param sortDirection comma separated sort direction for each sortBy field
     * @return list of paginated and sorted items
     */
    @GetMapping("/list")
    public ItemResponse getItemList(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize
            , @RequestParam("sortBy") String sortBy, @RequestParam("sortDirection") String sortDirection) {
        return itemService.getItemList(pageNo, pageSize, sortBy, sortDirection);
    }

    /**
     * Get Item by Id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ItemResponse getItemById(@PathVariable("id") Long id) {
        return itemService.getItemById(id);
    }

    /**
     * Get Item by Category
     *
     * @param category
     * @return
     */
    @GetMapping("/category/{category}")
    public ItemResponse getItemByCategory(@PathVariable("category") String category) {
        return itemService.getItemByCategory(CategoryType.getCategoryByValue(category));
    }

    /**
     * Save or Update Item
     *
     * @param request
     * @return
     */
    @PostMapping("/save")
    public ItemResponse saveItem(@Valid @RequestBody ItemRequest request) {
        return itemService.saveItem(request);
    }

}
