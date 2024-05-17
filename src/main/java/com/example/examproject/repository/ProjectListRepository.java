package com.example.examproject.repository;

import com.example.examproject.model.Project;
import com.example.examproject.model.ProjectList;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public Project findIDBProjectName(String projectName) {
        String sql = """
                SELECT projectID, projectName, projectDescription, projectStartDate, projectBudget, projectDueDate FROM projects WHERE projectName = ?
                """;

        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, projectName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                project = new Project(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDouble(5),
                        rs.getDate(6).toLocalDate()
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return project;
    }

    public Project findProject(int projectID) {
        String sql = """
        SELECT projectID, projectName, projectDescription, projectStartDate, projectBudget, projectDueDate
        FROM projects 
        WHERE projectID = ?
        """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                project = new Project(
                        rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDate(4).toLocalDate(),
                    rs.getDouble(5),
                    rs.getDate(6).toLocalDate()
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
            SELECT projects.projectID, projects.projectName, projects.projectDescription, projects.projectStartDate, projects.projectBudget, projects.dueDate, projects.completionDate, users.userName 
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
                project.setDueDate(resultSet.getDate("dueDate").toLocalDate());
                project.setCompletionDate(resultSet.getDate("completionDate").toLocalDate());

                projects.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return projects;
    }
}
