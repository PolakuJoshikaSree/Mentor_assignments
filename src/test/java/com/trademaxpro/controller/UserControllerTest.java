package com.trademaxpro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trademaxpro.model.User;
import com.trademaxpro.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class})
class UserControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockBean UserService userService;

    @Test
    void register_success() throws Exception {
        User u = new User("1","Joshika","js@mail.com","PAN",null,null);
        when(userService.registerUser(any())).thenReturn(u);

        mvc.perform(post("/api/users/register")
                .contentType("application/json")
                .content(mapper.writeValueAsString(u)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void getUser_success() throws Exception {
        User u = new User("1","Ana","a@mail.com","PAN",null,null);
        when(userService.getUser("1")).thenReturn(u);

        mvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana"));
    }
}
