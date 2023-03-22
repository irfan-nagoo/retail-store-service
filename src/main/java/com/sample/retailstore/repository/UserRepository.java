package com.sample.retailstore.repository;

import com.sample.retailstore.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author irfan.nagoo
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
