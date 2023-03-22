package com.sample.retailstore.repository;

import com.sample.retailstore.entity.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author irfan.nagoo
 */

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
}
