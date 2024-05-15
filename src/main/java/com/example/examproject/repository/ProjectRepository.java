package com.example.examproject.repository;

import com.example.examproject.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void showBudget() {
    }

    // RowMapper to map SQL result to Project object
    private static final class ProjectRowMapper implements RowMapper<Project> {
        @Override
        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
            Project project = new Project();
            project.setProjectId(rs.getInt("project_id"));
            project.setProjectName(rs.getString("project_name"));
            project.setProjectDescription(rs.getString("project_description"));
            project.setProjectStartDate(rs.getDate("project_start_date").toLocalDate());
            project.setDueDate(rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null);
            project.setProjectBudget(rs.getDouble("project_budget"));
            project.setCompletionDate(rs.getDate("completion_date") != null ? rs.getDate("completion_date").toLocalDate() : null);
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
        String sql = "SELECT * FROM projects WHERE completion_date IS NOT NULL";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    public List<Project> findOverdueProjects() {
        LocalDate today = LocalDate.now();
        String sql = "SELECT * FROM projects WHERE due_date < ? AND completion_date IS NULL";
        return jdbcTemplate.query(sql, new ProjectRowMapper(), today);
    }

    public List<Project> findAllProjects() {
        String sql = "SELECT * FROM projects";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    public LocalDate showProjectTime(int projectId) {
        String sql = "SELECT completion_date FROM projects WHERE project_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getDate("completion_date").toLocalDate(), projectId);
    }
}
