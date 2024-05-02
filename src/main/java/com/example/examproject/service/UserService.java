package com.example.examproject.service;

import com.example.examproject.model.User;
import com.example.examproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(User newUser) {
        return userRepository.registerUser(newUser);
    }
    public boolean loginUser(String username, String password) {
        return userRepository.loginUser(username, password);
    }
    public int findUserId(String userName){
        return userRepository.findUserById(userName);
    }

    public boolean editUser(User user){
        return userRepository.editUser(user);
    }
    public boolean deleteUser(String userName){
        return userRepository.deleteUser(userName);
    }
    public boolean validateUser(String userName, String password) {
        return userRepository.validateUser(userName, password);
    }
}
