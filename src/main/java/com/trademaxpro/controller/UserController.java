package com.trademaxpro.controller;

import com.trademaxpro.model.User;
import com.trademaxpro.service.UserService;
import org.springframework.web.bind.annotation.*;

// user apis: register, profile
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/{id}")
    public User viewProfile(@PathVariable String id) {
        return userService.getUser(id);
    }
}
