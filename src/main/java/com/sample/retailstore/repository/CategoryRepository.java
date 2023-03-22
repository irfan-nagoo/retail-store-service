package com.sample.retailstore.repository;

import com.sample.retailstore.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author irfan.nagoo
 */

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
