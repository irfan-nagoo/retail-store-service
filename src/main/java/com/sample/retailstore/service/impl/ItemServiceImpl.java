package com.sample.retailstore.service.impl;

import com.sample.retailstore.cache.CategoryCache;
import com.sample.retailstore.constants.CategoryType;
import com.sample.retailstore.domain.ItemObject;
import com.sample.retailstore.entity.Category;
import com.sample.retailstore.entity.Item;
import com.sample.retailstore.exception.RecordNotFoundException;
import com.sample.retailstore.mapper.ItemMapper;
import com.sample.retailstore.repository.ItemRepository;
import com.sample.retailstore.request.ItemRequest;
import com.sample.retailstore.response.ItemResponse;
import com.sample.retailstore.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sample.retailstore.constants.MessageConstants.*;

/**
 * @author irfan.nagoo
 */

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    private final CategoryCache categoryCache;

    @Override
    public ItemResponse getItemList(int pageNo, int pageSize, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, getOrderBy(sortBy, sortDirection));
        List<ItemObject> itemObjList = itemRepository.findAll(pageRequest).get()
                .map(itemMapper::itemToItemObject)
                .collect(Collectors.toList());
        ItemResponse response = new ItemResponse(HttpStatus.OK.name(), String.format(LIST_SUCCESS_MSG, itemObjList.size()));
        response.setItems(itemObjList);
        return response;
    }

    @Override
    public ItemResponse getItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            ItemResponse response = new ItemResponse(HttpStatus.OK.name(), GET_SUCCESS_MSG);
            response.getItems().add(itemMapper.itemToItemObject(item.get()));
            return response;
        } else {
            throw new RecordNotFoundException("Item Not Found");
        }
    }

    @Override
    public ItemResponse getItemByCategory(CategoryType categoryType) {
        List<Category> subCategoryList = categoryCache.getSubCategoryList(categoryType);
        List<Item> items = itemRepository.findByCategoryIn(subCategoryList);
        if (!items.isEmpty()) {
            ItemResponse response = new ItemResponse(HttpStatus.OK.name(), GET_SUCCESS_MSG);
            response.setItems(items.stream()
                    .map(itemMapper::itemToItemObject)
                    .collect(Collectors.toList()));
            return response;
        } else {
            throw new RecordNotFoundException("Item Not Found");
        }
    }


    @Override
    public ItemResponse saveItem(ItemRequest request) {
        Item item = itemMapper.itemObjectToItem(request.getItem());
        item = itemRepository.save(item);
        ItemResponse response = new ItemResponse(HttpStatus.OK.name(), SAVE_SUCCESS_MSG);
        response.getItems().add(itemMapper.itemToItemObject(item));
        return response;
    }

    private Sort getOrderBy(String sortBy, String sortDirection) {
        String[] sortByArr = sortBy.split(",");
        String[] sortDirectionArr = sortDirection.split(",");
        Sort sort = Sort.by(Sort.Direction.valueOf(sortDirectionArr[0].toUpperCase()), sortByArr[0]);
        for (int i = 1; i < sortByArr.length; i++) {
            sort = sort.and(Sort.by(Sort.Direction.valueOf(sortDirectionArr[i].toUpperCase()), sortByArr[i]));
        }
        return sort;
    }

}
