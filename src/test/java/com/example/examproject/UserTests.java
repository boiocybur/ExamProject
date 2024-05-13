package com.example.examproject;

import com.example.examproject.model.User;
import com.example.examproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testLoginUser() {
        assertTrue(userRepository.loginUser("Oskar", "1234"));
        assertFalse(userRepository.loginUser("testuser", "password"));
    }

    @Test
    public void testDeleteUser() {
        assertTrue(userRepository.deleteUser("Oskar"));

        assertNull(userRepository.getUserById(1));
    }
}
