package com.sample.retailstore.service.impl;

import com.sample.retailstore.domain.UserObject;
import com.sample.retailstore.entity.User;
import com.sample.retailstore.exception.RecordNotFoundException;
import com.sample.retailstore.mapper.UserMapper;
import com.sample.retailstore.repository.UserRepository;
import com.sample.retailstore.request.UserRequest;
import com.sample.retailstore.response.UserResponse;
import com.sample.retailstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.sample.retailstore.constants.MessageConstants.*;

/**
 * @author irfan.nagoo
 */


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserResponse getUserList() {
        List<UserObject> userObjList = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(userMapper::userToUserObject)
                .collect(Collectors.toList());
        UserResponse response = new UserResponse(HttpStatus.OK.name(), String.format(LIST_SUCCESS_MSG, userObjList.size()));
        response.setUsers(userObjList);
        return response;
    }

    @Override
    public UserResponse getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserResponse response = new UserResponse(HttpStatus.OK.name(), GET_SUCCESS_MSG);
            response.getUsers().add(userMapper.userToUserObject(user.get()));
            return response;
        } else {
            throw new RecordNotFoundException("User Not Found");
        }
    }

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        User user = userMapper.userObjectToUser(userRequest.getUser());
        user = userRepository.save(user);
        UserResponse response = new UserResponse(HttpStatus.OK.name(), SAVE_SUCCESS_MSG);
        response.getUsers().add(userMapper.userToUserObject(user));
        return response;
    }
}
