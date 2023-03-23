package com.sample.retailstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.domain.UserObject;
import com.sample.retailstore.request.UserRequest;
import com.sample.retailstore.response.UserResponse;
import com.sample.retailstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUserList_Success() throws Exception {
        when(userService.getUserList()).thenReturn(new UserResponse(HttpStatus.OK.name(), "Some message"));
        mockMvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    @Test
    void getUserById_Success() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(new UserResponse(HttpStatus.OK.name(), "Some message"));
        mockMvc.perform(get("/user/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }


    @Test
    void saveUser_Success() throws Exception {
        when(userService.saveUser(any())).thenReturn(new UserResponse(HttpStatus.OK.name(), "Some message"));
        UserRequest request = new UserRequest(getUserObject());
        String userJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HttpStatus.OK.name())));
    }

    private UserObject getUserObject() {
        UserObject userObject = new UserObject();
        userObject.setName("Sample User");
        userObject.setAddress("Test Address");
        userObject.setEmail("test@yahoo.com");
        userObject.setMobile("981234345676");
        userObject.setStatus(StatusType.USER_ACTIVE);
        userObject.setCreatedBy("TestUser");
        userObject.setUpdatedBy("TestUser");
        return userObject;
    }
}