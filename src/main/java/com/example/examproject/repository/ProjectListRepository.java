package com.example.examproject.repository;

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
    String db_url;
    @Value("${spring.datasource.username}")
    String uid;
    @Value("${spring.datasource.password}")
    String pwd;

    private ProjectList projectList;


    public List<ProjectList> showAllProjectLists() {
        List<ProjectList> allProjectLists = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, uid, pwd);
        String sql = "SELECT project.projectId, project.projectName, project.projectDescription FROM projectList JOIN project ON projectList.projectListID = project.projectListID";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                projectList = new ProjectList(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                allProjectLists.add(projectList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allProjectLists;
    }

    public List<ProjectList> showProjectList(int projectListId) {
        List<ProjectList> projectLists = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, uid, pwd);
        String sql = "SELECT project.projectId, project.projectName, project.projectDescription FROM projectList JOIN project ON projectList.projectListID = project.projectListID WHERE projectList.projectListID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, projectListId);
            ResultSet rs = ps.executeQuery(); // Corrected this line
            while (rs.next()) {
                projectList = new ProjectList(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                projectLists.add(projectList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectLists;
    }


    public ProjectList createProjectList(ProjectList projectList) {
        Connection connection = ConnectionManager.getConnection(db_url, uid, pwd);
        String sql = "INSERT INTO projectList (projectListName) VALUES(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, projectList.getProjectListName());
            ps.executeUpdate();
            return projectList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteProjectlist(int projectListID) {
        int rows = 0;
        Connection connection = ConnectionManager.getConnection(db_url, uid, pwd);
        String sql = "DELETE FROM projectList WHERE projectListId = ?";
        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, projectListID);
            rows = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows == 1;
    }

    public ProjectList searchToUpdate(int projectListID) {
        ProjectList projectList1 = null;
        Connection connection = ConnectionManager.getConnection(db_url, uid, pwd);
        String sql = "SELECT projectListID, projectListName, projectListDescription FROM projectList WHERE projectList.projectListID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, projectListID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                projectList1 = new ProjectList(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
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
        WHERE project.projectListID = projectList.projectListID)
        """;
        Connection connection = ConnectionManager.getConnection(db_url, uid, pwd);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, projectList.getProjectListName());
            preparedStatement.setInt(2, projectList.getProjectListID());
            rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows == 1;
    }
}
