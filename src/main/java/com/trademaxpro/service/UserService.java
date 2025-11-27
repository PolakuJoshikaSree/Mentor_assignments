package com.trademaxpro.service;

import com.trademaxpro.exception.UserNotFoundException;
import com.trademaxpro.model.User;
import com.trademaxpro.repository.UserRepository;
import org.springframework.stereotype.Service;

// handles user operations
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }

}
