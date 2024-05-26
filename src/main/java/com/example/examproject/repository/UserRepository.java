package com.example.examproject.repository;

import com.example.examproject.model.User;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    @Value("${spring.datasource.url}")
    String dbUrl;
    @Value("${spring.datasource.username}")
    String dbUserName;
    @Value("${spring.datasource.password}")
    String dbPassword;

    public int findUserById(String userEmail) {
        int userid = 0;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String query = "SELECT userID FROM users WHERE userEmail = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userEmail);
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
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = "SELECT * FROM users WHERE userID = ?";
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userid"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString(("userpassword")));
                user.setUserEmail(resultSet.getString("userEmail"));
                user.setUserRank(resultSet.getString("userRank"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public boolean existingEmail(String userEmail) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String query = "SELECT COUNT(*) FROM users WHERE useremail = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userEmail);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean createUser(User newUser) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String query = "INSERT INTO users (userName, userPassword, userEmail, userRank) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newUser.getUserName());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getUserEmail());
            preparedStatement.setString(4, newUser.getUserRank());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean loginUser(String userEmail, String password) {
        //Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String query = "SELECT COUNT(*) AS count FROM users WHERE userEmail = ? AND userPassword = ?";
        try (Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, userEmail);
            pstmt.setString(2, password);

            try (ResultSet resultSet = pstmt.executeQuery()) {
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
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String SQL = "UPDATE users SET userName = ?, userPassword = ?, userEmail = ? , userRank = ? WHERE userID = ? ";
        try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getUserEmail());
            pstmt.setString(4, user.getUserRank());
            pstmt.setInt(5, user.getUserId());

            rows = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (rows == 1)
            return user;
        else
            return null;
    }
    public boolean deleteUser(String userEmail) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String query = "DELETE FROM users WHERE useremail = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userEmail);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userID"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("userPassword"));
                user.setUserEmail(resultSet.getString("userEmail"));
                user.setUserRank(resultSet.getString("userRank"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
