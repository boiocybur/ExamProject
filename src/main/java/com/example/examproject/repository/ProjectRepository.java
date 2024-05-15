package com.example.examproject.repository;

import com.example.examproject.model.Project;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

        private Connection getConnection() throws SQLException {
            return DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        }

        private Project projectMap(ResultSet rs) throws SQLException {
            Project project = new Project();
            project.setProjectID(rs.getInt("projectID"));
            project.setProjectName(rs.getString("projectName"));
            project.setProjectDescription(rs.getString("projectDescription"));
            Date startDate = rs.getDate("projectStartDate");
            if (startDate != null) {
                project.setProjectStartDate(startDate.toLocalDate());
            }
            project.setProjectBudget(rs.getDouble("projectBudget"));
            Date dueDate = rs.getDate("dueDate");
            if (dueDate != null) {
                project.setDueDate(dueDate.toLocalDate());
            }
            Date completionDate = rs.getDate("completionDate");
            if (completionDate != null) {
                project.setCompletionDate(completionDate.toLocalDate());
            }
            return project;
        }

        private List<Project> executeProjectQuery(String sql, LocalDate... params) {
            List<Project> projects = new ArrayList<>();
            Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
            LocalDate imminentDeadline = today.plusDays(3);
            String sql = "SELECT * FROM project WHERE dueDate BETWEEN ? AND ? AND completionDate IS NULL";
            return executeProjectQuery(sql, today, imminentDeadline);
        }


        public List<Project> findCompletedProjects() {
            String sql = "SELECT * FROM project WHERE completionDate IS NOT NULL";
            return executeProjectQuery(sql);

        }

        public List<Project> findOverdueProjects() {
            LocalDate today = LocalDate.now();
            String sql = "SELECT * FROM project WHERE dueDate < ? AND completionDate IS NULL";
            return executeProjectQuery(sql, today);
        }

        public List<Project> findAllProjects() {
            List<Project> projects = new ArrayList<>();
            String sql = "SELECT * FROM project";
            Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Project project = new Project(resultSet.getString("projectName"));
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