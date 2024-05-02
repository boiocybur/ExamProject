package com.example.examproject.model;

public class User {
    private String userName;
    private String password;
    private int userId;
    private String userRank;

    public User() {

    }

    public User(String userName, String password, int userId, String userRank) {
        this.userName = userName;
        this.password = password;
        this.userId = userId;
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

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }
}
