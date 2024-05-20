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
public class ProjectListRepository {
    @Value("${spring.datasource.url}")
    String dbUrl;
    @Value("${spring.datasource.username}")
    String dbUserName;
    @Value("${spring.datasource.password}")
    String dbPassword;

    private Project project;



    public Project findIDBProjectName(String projectName) {
        String sql = """
                SELECT projectName, projectDescription, projectID, projectStartDate, projectDueDate, projectBudget FROM projects WHERE projectName = ?
                """;

        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, projectName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                project = new Project(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate(),
                        rs.getDouble(6)
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return project;
    }

    public Project findProjectNoCompletionDate(int projectID) {
        String sql = """
        SELECT projectName, projectDescription, projectID, projectStartDate, projectDueDate, projectBudget
        FROM projects 
        WHERE projectID = ?
        """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                project = new Project(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate(),
                        rs.getDouble(6)
                );

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return project;
    }

    public Project findProjectWithCompletionDate(int projectID) {
        String sql = """
        SELECT projectName, projectDescription, projectID, projectStartDate, projectDueDate, projectBudget, projectCompletionDate
        FROM projects 
        WHERE projectID = ?
        """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                project = new Project(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDate(4) != null ? rs.getDate(4).toLocalDate() : null ,
                        rs.getDate(5) != null ? rs.getDate(5).toLocalDate() : null,
                        rs.getDouble(6),
                        rs.getDate(7) != null ? rs.getDate(7).toLocalDate() : null
                );

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return project;
    }

    public boolean updateProject(Project project) {
        int rows = 0;
        String sql = """
        UPDATE projects SET projectName = ?, projectDescription = ?, projectStartDate = ?, projectBudget = ?, projectDueDate = ?
        WHERE projectID = ?
        """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, project.getProjectName());
            ps.setString(2, project.getProjectDescription());
            ps.setDate(3, Date.valueOf(project.getProjectStartDate()));
            ps.setDouble(4, project.getProjectBudget());
            ps.setDate(5, Date.valueOf(project.getProjectDueDate()));
            ps.setInt(6, project.getProjectID());
            rows = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows == 1;
    }

    public void createProject(Project project, int userID) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = "INSERT INTO projects (projectName, projectDescription, projectStartDate, projectBudget, projectDueDate, userID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getProjectDescription());
            pstmt.setDate(3, Date.valueOf(project.getProjectStartDate()));
            pstmt.setDouble(4, project.getProjectBudget());
            pstmt.setDate(5, Date.valueOf(project.getProjectDueDate()));
            pstmt.setInt(6, userID);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Project> getOpenProjectsCreatedByUser(int userID) {
        List<Project> items = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = """
                SELECT projectName, projectDescription, projectID 
                FROM projects 
                JOIN users ON projects.userID = users.userID 
                WHERE users.userID = ? AND projects.projectCompletionDate IS NULL
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                project = new Project(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3)
                );
                items.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }


    public List<Project> getClosedProjectsCreatedByUser(int userID) {
        List<Project> projects = new ArrayList<>();
        String sql = """
            SELECT projects.projectID, projects.projectName, projects.projectDescription, projects.projectStartDate, projects.projectBudget, projects.projectDueDate, projects.completionDate, users.userName 
            FROM projects
            LEFT JOIN users ON projects.userID = users.userID
            WHERE users.userID = ? AND projects.completionDate IS NOT NULL
            """;


        try (Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Project project = new Project();
                project.setProjectID(resultSet.getInt("projectID"));
                project.setProjectName(resultSet.getString("projectName"));
                project.setProjectDescription(resultSet.getString("projectDescription"));
                project.setProjectStartDate(resultSet.getDate("projectStartDate").toLocalDate());
                project.setProjectBudget(resultSet.getDouble("projectBudget"));
                project.setProjectDueDate(resultSet.getDate("projectDueDate").toLocalDate());
                project.setCompletionDate(resultSet.getDate("completionDate").toLocalDate());

                projects.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return projects;
    }
}
