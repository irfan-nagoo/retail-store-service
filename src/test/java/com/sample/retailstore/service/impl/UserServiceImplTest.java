package com.sample.retailstore.service.impl;

import com.sample.retailstore.domain.UserObject;
import com.sample.retailstore.entity.User;
import com.sample.retailstore.exception.RecordNotFoundException;
import com.sample.retailstore.mapper.UserMapperImpl;
import com.sample.retailstore.repository.UserRepository;
import com.sample.retailstore.request.UserRequest;
import com.sample.retailstore.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapperImpl userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserList() {
        UserObject userObject = new UserObject();
        userObject.setName("TestUser");
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));
        when(userMapper.userToUserObject(any())).thenReturn(userObject);
        UserResponse response = userService.getUserList();
        assertEquals(1, response.getUsers().size());
        assertEquals("TestUser", response.getUsers().get(0).getName());
    }

    @Test
    void getUserById_Success() {
        UserObject userObject = new UserObject();
        userObject.setName("TestUser");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(userMapper.userToUserObject(any())).thenReturn(userObject);
        UserResponse response = userService.getUserById(1L);
        assertEquals(1, response.getUsers().size());
        assertEquals("TestUser", response.getUsers().get(0).getName());
    }

    @Test
    void getUserById_Exception() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> userService.getUserById(1L));
        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    void saveUser_Success() {
        UserObject userObject = new UserObject();
        userObject.setName("TestUser");
        User user = new User();
        when(userMapper.userObjectToUser(any())).thenReturn(user);
        when(userMapper.userToUserObject(any())).thenReturn(userObject);
        when(userRepository.save(any())).thenReturn(user);
        UserResponse response = userService.saveUser(new UserRequest(userObject));
        assertEquals(1, response.getUsers().size());
        assertEquals("TestUser", response.getUsers().get(0).getName());
    }

}