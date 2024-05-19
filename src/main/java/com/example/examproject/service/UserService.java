package com.example.examproject.service;

import com.example.examproject.model.User;
import com.example.examproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean createUser(User newUser) {
        if (userRepository.existingEmail(newUser.getUserEmail())) {
            return false;
        }
        return userRepository.createUser(newUser);
    }
    public boolean loginUser(String userEmail, String password) {
        return userRepository.loginUser(userEmail, password);
    }
    public int findUserById(String userEmail){
        return userRepository.findUserById(userEmail);
    }
    public User getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    public User editUser(User user){
        return userRepository.editUser(user);
    }
    public boolean deleteUser(String userEmail){
        return userRepository.deleteUser(userEmail);
    }
    public boolean isAdmin(int userId) {
        User user = getUserById(userId);
        return user != null && "admin".equals(user.getUserRank());
    }
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }
}
