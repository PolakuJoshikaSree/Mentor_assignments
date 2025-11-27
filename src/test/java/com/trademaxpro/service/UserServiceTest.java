package com.trademaxpro.service;

import com.trademaxpro.exception.UserNotFoundException;
import com.trademaxpro.model.User;
import com.trademaxpro.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    void registerUser_savesUser() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        User user = new User();
        Mockito.when(repo.save(user)).thenReturn(user);

        User saved = service.registerUser(user);

        assertNotNull(saved);
    }

    @Test
    void getUser_notFound_throws() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        Mockito.when(repo.findById("u1")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> service.getUser("u1"));
    }
}
