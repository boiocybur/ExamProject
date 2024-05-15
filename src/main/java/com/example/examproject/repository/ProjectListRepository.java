package com.example.examproject.repository;

import com.example.examproject.model.Project;
import com.example.examproject.model.ProjectList;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

    private ProjectList projectList;
    private Project project;


    public List<ProjectList> showAllProjectLists() {
        List<ProjectList> allProjectLists = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = "SELECT project.projectId, project.projectName, project.projectDescription FROM projectList JOIN project ON projectList.projectListID = project.projectListID";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                projectList = new ProjectList(
                        rs.getInt(1),
                        rs.getString(2)

                );
                allProjectLists.add(projectList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allProjectLists;
    }

    public List<ProjectList> showProjectList(int userID) {
        List<ProjectList> projectLists = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = "SELECT projectListID, projectListName FROM projectList WHERE userID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(3, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                projectList = new ProjectList(
                        rs.getInt(1),
                        rs.getString(2)
                );
                projectLists.add(projectList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectLists;
    }


    public ProjectList createProjectList(ProjectList projectList) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = "INSERT INTO projectList (projectListName) VALUES(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, project.getProjectName());
            ps.executeUpdate();
            return projectList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteProjectList(int userID) {
        int rows = 0;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = "DELETE FROM projectList WHERE userID = ?";
        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            rows = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows == 1;
    }

    public ProjectList searchToUpdate(int projectListID) {
        ProjectList projectList1 = null;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = "SELECT projectListID, projectListName, projectListDescription FROM projectList WHERE projectList.projectListID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, projectListID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                projectList1 = new ProjectList(
                        rs.getInt(1),
                        rs.getString(2)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectList1;
    }

    public boolean updateProjectList(ProjectList projectList) {
        int rows = 0;
        String sql = """
        UPDATE projectList SET projectListName = ?
        WHERE projectListID = ? AND EXISTS (SELECT 1 FROM project
        WHERE projectListID = projectList.projectListID)
        """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, projectList.getProjectListName());
            preparedStatement.setInt(2, projectList.getProjectListID());
            rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows == 1;
    }

    public void createProject(Project project, int userID) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = "INSERT INTO projects (projectName, projectDescription, projectStartDate, projectBudget, dueDate, completionDate, userID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getProjectDescription());
            pstmt.setDate(3, Date.valueOf(project.getProjectStartDate()));
            pstmt.setDouble(4, project.getProjectBudget());
            pstmt.setDate(5, Date.valueOf(project.getProjectDueDate()));
            pstmt.setDate(6, Date.valueOf(project.getCompletionDate()));
            pstmt.setInt(7, userID);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
