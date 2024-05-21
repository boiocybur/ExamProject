package com.example.examproject.model;

import java.util.Objects;
import java.util.List;

public class User {
    private String userName;
    private String password;
    private int userId;
    private String userEmail;
    private String userRank;
    public User() {

    }

    public User(String userName, String password, int userId, String userEmail, String userRank) {
        this.userName = userName;
        this.password = password;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userRank = userRank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getUserId() == user.getUserId() && Objects.equals(getUserName(), user.getUserName()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getPassword(), getUserId());
    }
}
