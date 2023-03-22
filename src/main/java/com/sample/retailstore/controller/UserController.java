package com.sample.retailstore.controller;

import com.sample.retailstore.request.UserRequest;
import com.sample.retailstore.response.UserResponse;
import com.sample.retailstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author irfan.nagoo
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Get User list
     *
     * @return
     */
    @GetMapping("/list")
    public UserResponse getItemList() {
        return userService.getUserList();
    }

    /**
     * Get User by Id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public UserResponse getItemById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    /**
     * Save or Update user
     *
     * @param request
     * @return
     */
    @PostMapping("/save")
    public UserResponse saveItem(@Valid @RequestBody UserRequest request) {
        return userService.saveUser(request);
    }

}
