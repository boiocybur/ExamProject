package com.example.examproject.repository;

import com.example.examproject.model.User;
import com.example.examproject.util.ConnectionManager;
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
        int userid = 0;
        String query = "SELECT userid FROM bruger WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(db_url, uid, pwd);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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
        String sql = "SELECT * FROM bruger WHERE userid = ?";
        User user = null;
        try (Connection connection = DriverManager.getConnection(db_url, uid, pwd);
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
        String query = "INSERT INTO bruger (username, userpassword) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(db_url, uid, pwd);
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
        String query = "SELECT COUNT(*) AS count FROM bruger WHERE username = ? AND userpassword = ?";

        try (Connection connection = DriverManager.getConnection(db_url, uid, pwd);
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
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            String SQL = "UPDATE bruger SET username = ?, userpassword = ? WHERE userid = ? ";
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
        String query = "DELETE FROM bruger WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(db_url, uid, pwd);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userName);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
