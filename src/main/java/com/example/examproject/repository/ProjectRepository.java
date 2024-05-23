package com.example.examproject.repository;

import com.example.examproject.model.Project;
import com.example.examproject.model.Task;
import com.example.examproject.model.User;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.password}")
    private String dbPassword;


    private Task task;
    private Project project;

    public List<Task> assignedTasks(int projectID) {
        List<Task> tasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = """
        SELECT * 
        FROM tasks 
        WHERE projectID = ?
        ORDER BY taskDueDate DESC
                    """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, projectID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate()
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }

    public List<Task> imminentAssignedTasks(int projectID) {
        List<Task> imminentTasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = """
                    SELECT * 
                    FROM tasks 
                    WHERE projectID = ? AND taskDueDate BETWEEN current_date AND current_date + INTERVAL 3 DAY
                    ORDER BY taskDueDate DESC
                    """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, projectID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate()
                );
                imminentTasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return imminentTasks;
    }

    public List<Task> overdueAssignedTasks(int projectID) {
        List<Task> overdueTasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = """
                SELECT * 
                FROM tasks 
                WHERE projectID = ? AND taskDueDate < current_date
                ORDER BY taskDueDate DESC
                """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate()
                );
                overdueTasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return overdueTasks;
    }



    public void createTask(Task task, int userID, int projectID) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = "INSERT INTO tasks (projectID, taskName, taskDescription, taskStartDate, taskDueDate, userID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, projectID);
            pstmt.setString(2, task.getTaskName());
            pstmt.setString(3, task.getTaskDescription());
            pstmt.setDate(4, Date.valueOf(task.getTaskStartDate()));
            pstmt.setDate(5, Date.valueOf(task.getTaskDueDate()));
            pstmt.setInt(6, userID);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateTask(Task task, int taskID) {
        int rows = 0;
        String sql = """
        UPDATE tasks 
        SET taskName = ?, taskDescription = ?, taskStartDate = ?, taskDueDate = ?
        WHERE taskID = ?
        """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getTaskDescription());
            ps.setDate(3, Date.valueOf(task.getTaskStartDate()));
            ps.setDate(4, Date.valueOf(task.getTaskDueDate()));
            ps.setInt(5, taskID);
            rows = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows == 1;
    }

    public Task findTask(int taskID) {
        String sql = """
        SELECT *
        FROM tasks 
        WHERE taskID = ?
        """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, taskID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                task = new Task(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate()
                );

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return task;
    }
    public boolean deleteTask(int taskID) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String query = "DELETE FROM tasks WHERE taskID = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, taskID);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void assignTaskToUser(int userID, int taskID) {
            try {
                Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
                String query = "INSERT INTO tasksanduser (taskID, userID) VALUES (?,?);";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, taskID);
                preparedStatement.setInt(2, userID);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    public Project findProjectById(int projectID) {
        String sql = """
    SELECT * 
    FROM projects 
    WHERE projectID = ?
    """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                project = new Project(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(1),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate(),
                        rs.getDouble(6),
                        rs.getDate(7) != null ? rs.getDate(7).toLocalDate() : null
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return project;
    }

    public double getBudgetSpent(int projectID) {
        Period period = Period.between(project.getProjectStartDate(), LocalDate.now());
        int daysSpent = period.getDays() + period.getMonths() * 30 + period.getYears() * 365;
        double dailyBudgetRate = project.getProjectBudget() / (Period.between(project.getProjectStartDate(), project.getProjectDueDate()).getDays() + 1);
        return daysSpent * dailyBudgetRate;
    }

    public double getBudgetRemaining(int projectID) {
        return project.getProjectBudget() - getBudgetSpent(projectID);
    }

    public void removeAssignedTaskToUser(int userID, int taskID) {
        try {
            Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
            String query = "DELETE FROM tasksanduser WHERE taskID=? AND userID=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, taskID);
            preparedStatement.setInt(2, userID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findAssignedUsersByTaskID(int taskID) {
        List<User> assignedUsers = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
            String query = "SELECT * FROM users WHERE userID IN \n" +
            "(SELECT userID FROM tasksanduser WHERE taskID = ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, taskID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userID"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("userPassword"));
                user.setUserEmail(resultSet.getString("userEmail"));
                user.setUserRank(resultSet.getString("userRank"));
                assignedUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return assignedUsers;
    }
}

