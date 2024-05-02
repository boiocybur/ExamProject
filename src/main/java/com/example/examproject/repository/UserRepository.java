package com.example.examproject.repository;

import com.example.examproject.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {
    @Value("${spring.datasource.url}")
    String db_url;
    @Value("${spring.datasource.username}")
    String uid;
    @Value("${spring.datasource.password}")
    String pwd;
    public int findUserById(String userName) {
       return 1;
    }

    public boolean registerUser(User newUser) {
      return true;
    }

    public boolean loginUser(String username, String password) {
      return true;
    }
    public boolean editUser(User user){
        return true;
    }
    public boolean deleteUser(String userName) {
       return true;
    }
    public boolean validateUser(String username, String password) {
        return true;
    }
}
