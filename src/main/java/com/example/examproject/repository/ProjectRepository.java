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
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private static final class ProjectRowMapper implements RowMapper<Project> {
        @Override
        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
            Project project = new Project();
            project.setProjectID(rs.getInt("projectID"));
            project.setProjectName(rs.getString("projectName"));
            project.setProjectDescription(rs.getString("projectDescription"));
            project.setProjectStartDate(rs.getDate("projectStartDate").toLocalDate());
            project.setProjectDueDate(rs.getDate("projectDueDate") != null ? rs.getDate("projectDueDate").toLocalDate() : null);
            project.setProjectBudget(rs.getDouble("projectBudget"));
            project.setCompletionDate(rs.getDate("projectCompletionDate") != null ? rs.getDate("completionDate").toLocalDate() : null);
            return project;
        }
    }

    public List<Project> findProjectsByImminentDeadlines() {
        LocalDate today = LocalDate.now();
        LocalDate imminentDeadline = today.plusDays(30);
        String sql = "SELECT * FROM projects WHERE projectDueDate BETWEEN ? AND ? AND projectCompletionDate IS NULL";
        try {
            return jdbcTemplate.query(sql, new ProjectRowMapper(), today, imminentDeadline);
        } catch (DataAccessException e) {
            // Log error details
            System.err.println("Error fetching imminent projects: " + e.getMessage());
            throw e; // Optionally, rethrow or handle exception accordingly
        }
    }
    public List<Project> findCompletedProjects() {
        String sql = "SELECT * FROM projects WHERE projectCompletionDate IS NOT NULL";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    public List<Project> findOverdueProjects() {
        LocalDate today = LocalDate.now();
        String sql = "SELECT * FROM projects WHERE projectDueDate < ? AND projectCompletionDate IS NULL";
        return jdbcTemplate.query(sql, new ProjectRowMapper(), today);
    }

    public List<Project> findAllProjects() {
        String sql = "SELECT * FROM projects";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    public LocalDate showProjectTime(int projectId) {
        String sql = "SELECT projectCompletionDate FROM projects WHERE projectID = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getDate("completionDate").toLocalDate(), projectId);
    }

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
    }
