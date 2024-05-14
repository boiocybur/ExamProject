package com.example.examproject.repository;

import com.example.examproject.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {
    @Value("${spring.datasource.url}")
    String dbUrl;
    @Value("${spring.datasource.username}")
    String dbUserName;
    @Value("${spring.datasource.password}")
    String dbPassword;
    public int findUserById(String userName) {
        int userid = 0;
        String sql = "SELECT userID FROM user WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userid = resultSet.getInt("userid");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userid;
    }
    public User getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE userID = ?";
        User user = null;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userid"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString(("userpassword")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public boolean createUser(User newUser) {
        String query = "INSERT INTO user (username, userpassword) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newUser.getUserName());
            preparedStatement.setString(2, newUser.getPassword());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean loginUser(String username, String password) {
        String query = "SELECT COUNT(*) AS count FROM user WHERE userName = ? AND userPassword = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public User editUser(User user) {
        int rows = 0;
        try (Connection con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            String SQL = "UPDATE user SET userName = ?, userPassword = ? WHERE userID = ? ";
            PreparedStatement pstmt = con.prepareStatement(SQL);

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getUserId());

            rows = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (rows == 1)
            return user;
        else
            return null;
    }
    public boolean deleteUser(String userName) {
        String query = "DELETE FROM user WHERE userName = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userName);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
