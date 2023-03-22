package com.sample.retailstore.repository;

import com.sample.retailstore.entity.Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author irfan.nagoo
 */

@Repository
public interface TypeRepository extends CrudRepository<Type, Long> {
}
