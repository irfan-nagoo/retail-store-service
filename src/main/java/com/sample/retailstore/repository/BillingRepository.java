package com.sample.retailstore.repository;

import com.sample.retailstore.entity.Billing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author irfan.nagoo
 */

@Repository
public interface BillingRepository extends CrudRepository<Billing, Long> {
}
