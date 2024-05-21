package com.example.examproject.repository;

import com.example.examproject.model.Project;
import com.example.examproject.model.Task;
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