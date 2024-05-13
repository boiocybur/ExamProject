package com.example.examproject.service;

import com.example.examproject.model.User;
import com.example.examproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean createUser(User newUser) {
        return userRepository.createUser(newUser);
    }
    public boolean loginUser(String username, String password) {
        return userRepository.loginUser(username, password);
    }
    public int findUserById(String userName){
        return userRepository.findUserById(userName);
    }
    public User getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    public void editUser(User user){
        userRepository.editUser(user);
    }
    public boolean deleteUser(String userName){
        return userRepository.deleteUser(userName);
    }
}
