package com.sample.retailstore.repository;

import com.sample.retailstore.entity.Order;
import com.sample.retailstore.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author irfan.nagoo
 */

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> findByUser(User user);
}
