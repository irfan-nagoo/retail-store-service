package com.sample.retailstore.service;

import com.sample.retailstore.request.UserRequest;
import com.sample.retailstore.response.UserResponse;

/**
 * @author irfan.nagoo
 */
public interface UserService {

    /**
     * Get list of users
     *
     * @return
     */
    UserResponse getUserList();

    /**
     * Get User by id
     *
     * @param id
     * @return
     */
    UserResponse getUserById(Long id);

    /**
     * Save or Update user record
     *
     * @param userRequest
     * @return
     */
    UserResponse saveUser(UserRequest userRequest);

}
