package com.sample.retailstore.repository;

import com.sample.retailstore.entity.Category;
import com.sample.retailstore.entity.Item;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author irfan.nagoo
 */

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

    List<Item> findByCategoryIn(List<Category> categoryList);
}
