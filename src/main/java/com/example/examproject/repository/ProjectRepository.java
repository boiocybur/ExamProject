package com.example.examproject.repository;

import com.example.examproject.model.Project;
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
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createProject(Project project) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = "INSERT INTO projects (projectName, projectDescription, projectStartDate, projectBudget, dueDate, completionDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getProjectDescription());
            pstmt.setDate(3, Date.valueOf(project.getProjectStartDate()));
            pstmt.setDouble(4, project.getProjectBudget());
            pstmt.setDate(5, Date.valueOf(project.getProjectDueDate()));
            pstmt.setDate(6, Date.valueOf(project.getCompletionDate()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static final class ProjectRowMapper implements RowMapper<Project> {
        @Override
        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
            Project project = new Project();
            project.setProjectID(rs.getInt("projectID")); // Opdateret kolonnenavn
            project.setProjectName(rs.getString("projectName")); // Opdateret kolonnenavn
            project.setProjectDescription(rs.getString("projectDescription")); // Opdateret kolonnenavn
            project.setProjectStartDate(rs.getDate("projectStartDate").toLocalDate()); // Opdateret kolonnenavn
            project.setDueDate(rs.getDate("dueDate") != null ? rs.getDate("dueDate").toLocalDate() : null); // Opdateret kolonnenavn
            project.setProjectBudget(rs.getDouble("projectBudget")); // Opdateret kolonnenavn
            project.setCompletionDate(rs.getDate("completionDate") != null ? rs.getDate("completionDate").toLocalDate() : null); // Opdateret kolonnenavn
            return project;
        }
    }

    public List<Project> findProjectsByImminentDeadlines() {
        LocalDate today = LocalDate.now();
        LocalDate imminentDeadline = today.plusDays(30);
        String sql = "SELECT * FROM projects WHERE due_date BETWEEN ? AND ? AND completion_date IS NULL";
        try {
            return jdbcTemplate.query(sql, new ProjectRowMapper(), today, imminentDeadline);
        } catch (DataAccessException e) {
            // Log error details
            System.err.println("Error fetching imminent projects: " + e.getMessage());
            throw e; // Optionally, rethrow or handle exception accordingly
        }
    }
    public List<Project> findCompletedProjects() {
        String sql = "SELECT * FROM projects WHERE completionDate IS NOT NULL";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    public List<Project> findOverdueProjects() {
        LocalDate today = LocalDate.now();
        String sql = "SELECT * FROM projects WHERE dueDate < ? AND completionDate IS NULL";
        return jdbcTemplate.query(sql, new ProjectRowMapper(), today);
    }

    public List<Project> findAllProjects() {
        String sql = "SELECT * FROM projects";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    public LocalDate showProjectTime(int projectId) {
        String sql = "SELECT completionDate FROM projects WHERE projectID = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getDate("completionDate").toLocalDate(), projectId);
    }
    public void showBudget() {
    }

    public void attachResourcesToTask() {
    }

    public void removeResourceFromTask() {

    }
    public void showResources() {
    }

    public Project findProjectById(int projectId) {
        return null;
    }

    public void updateProjectBudget(Project project) {
    }
}