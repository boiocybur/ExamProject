package com.example.examproject.repository;

import com.example.examproject.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

    @Repository
    public class ProjectRepository {

        @Value("${spring.datasource.url}")
        private String dbUrl;
        @Value("${spring.datasource.username}")
        private String dbUsername;
        @Value("${spring.datasource.password}")
        private String dbPassword;

        @Autowired
        private DataSource dataSource;

        private Connection getConnection() throws SQLException {
            return dataSource.getConnection();
        }

        private Project projectMap(ResultSet rs) throws SQLException {
            Project project = new Project();
            project.setProjectId(rs.getInt("project_id"));
            project.setProjectName(rs.getString("project_name"));
            project.setProjectDescription(rs.getString("project_description"));
            Date startDate = rs.getDate("project_start_date");
            if (startDate != null) {
                project.setProjectStartDate(startDate.toLocalDate());
            }
            project.setProjectBudget(rs.getDouble("project_budget"));
            Date dueDate = rs.getDate("due_date");
            if (dueDate != null) {
                project.setDueDate(dueDate.toLocalDate());
            }
            Date completionDate = rs.getDate("completion_date");
            if (completionDate != null) {
                project.setCompletionDate(completionDate.toLocalDate());
            }
            return project;
        }

        private List<Project> executeProjectQuery(String sql, LocalDate... params) {
            List<Project> projects = new ArrayList<>();
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setDate(i + 1, Date.valueOf(params[i])); // Correctly converting LocalDate to sql.Date
                }
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        projects.add(projectMap(resultSet));
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error executing project query: " + e.getMessage());
            }
            return projects;
        }



        public List<Project> findProjectsByImminentDeadlines() {
            LocalDate today = LocalDate.now();
            LocalDate imminentDeadline = today.plusDays(7);
            String sql = "SELECT * FROM projects WHERE due_date BETWEEN ? AND ? AND completion_date IS NULL";
            return executeProjectQuery(sql, today, imminentDeadline);
        }


        public List<Project> findCompletedProjects() {
            String sql = "SELECT * FROM projects WHERE completion_date IS NOT NULL";
            return executeProjectQuery(sql);

        }

        public List<Project> findOverdueProjects() {
            LocalDate today = LocalDate.now();
            String sql = "SELECT * FROM projects WHERE due_date < ? AND completion_date IS NULL";
            return executeProjectQuery(sql, today);
        }

        public List<Project> findAllProjects() {
            List<Project> projects = new ArrayList<>();
            String sql = "SELECT * FROM projects";
            try (Connection conn = getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Project project = new Project(resultSet.getString("project_name"));
                    projects.add(project);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching all projects: " + e.getMessage());
            }
            return projects;
        }

        public void showBudget() {
        }

        public LocalDate showProjectTime(int projectId) {
            return null;
        }

        public void showResources() {
        }

        public void showUnfinishedTasks() {
        }

        public void showFinishedTasks() {
        }

        public void showOverdueTasks() {
        }

        public void attachResourcesToTask() {
        }

        public void removeResourceFromTask() {
        }
    }