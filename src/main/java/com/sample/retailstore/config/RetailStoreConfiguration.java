package com.sample.retailstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author irfan.nagoo
 */


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.sample.retailstore.repository")
public class RetailStoreConfiguration {


}
