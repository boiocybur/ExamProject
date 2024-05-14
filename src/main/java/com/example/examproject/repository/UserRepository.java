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
  
    public int findUserById(String userEmail) {
        int userid = 0;
        String query = "SELECT userid FROM bruger WHERE useremail = ?";

        try (Connection connection = DriverManager.getConnection(dburl, dbUserName, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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
                user.setUserEmail(resultSet.getString("userEmail"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
  
    public boolean existingEmail(String userEmail) {
        String query = "SELECT COUNT(*) FROM bruger WHERE useremail = ?";
        try (Connection connection = DriverManager.getConnection(dburl, dbUserName, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
        String query = "INSERT INTO bruger (username, userpassword, useremail) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newUser.getUserName());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getUserEmail());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean loginUser(String userEmail, String password) {
        String query = "SELECT COUNT(*) AS count FROM bruger WHERE useremail = ? AND userpassword = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userEmail);
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

        String SQL = "UPDATE bruger SET username = ?, userpassword = ?, useremail = ? WHERE userid = ? ";
        try (Connection connection = DriverManager.getConnection(dburl, dbUserName, dbPassword);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getUserEmail());
            pstmt.setInt(4, user.getUserId());

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
        String query = "DELETE FROM bruger WHERE useremail = ?";


        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userEmail);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
