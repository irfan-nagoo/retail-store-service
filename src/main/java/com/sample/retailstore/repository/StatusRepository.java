package com.sample.retailstore.repository;

import com.sample.retailstore.entity.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author irfan.nagoo
 */

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {
}
