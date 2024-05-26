package com.example.examproject.repository;

import com.example.examproject.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("h2")
class UserRepositoryTest {
@Autowired
UserRepository userRepository;

@BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setUserId(1);
        user1.setUserName("Oskar");
        user1.setPassword("1234");
        user1.setUserEmail("Oskar@mail.dk");
        user1.setUserRank("admin");

        User user2 = new User();
        user2.setUserId(2);
        user2.setUserName("Mikkel");
        user2.setPassword("1234");
        user2.setUserEmail("Mikkel@mail.dk");
        user2.setUserRank("admin");

        userRepository.createUser(user1);
        userRepository.createUser(user2);
    }
    @Test
    void existingEmail() {
        String email = "Mikkel@mail.dk";
        boolean actualResult = userRepository.existingEmail(email);
        assertTrue(actualResult);
    }

    @Test
    void creatUser() {
        User user = new User();
        user.setUserName("Henrik");
        user.setPassword("1234");
        user.setUserEmail("Henrik@mail.dk");
        user.setUserRank("generic");

        userRepository.createUser(user);
        boolean actualResult = userRepository.existingEmail("Henrik@mail.dk");
        assertTrue(actualResult);
    }

    @Test
    void editUser() {
        User user = userRepository.getUserById(1);
        user.setUserEmail("newOskar@mail.dk");
        userRepository.editUser(user);

        User updatedUser = userRepository.getUserById(1);
        assertEquals("newOskar@mail.dk", updatedUser.getUserEmail());
    }
    @Test
    void deleteUser() {
        boolean deleted = userRepository.deleteUser("Mikkel@mail.dk");
        assertTrue(deleted);
        assertFalse(userRepository.existingEmail("Mikkel@mail.dk"));
    }
}