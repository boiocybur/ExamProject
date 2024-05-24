package com.example.examproject.repository;

import com.example.examproject.model.TaskAcceptCriteria;
import com.example.examproject.model.Project;
import com.example.examproject.model.Task;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
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
    private TaskAcceptCriteria taskAcceptCriteria;
    

    public List<Task> openTasks(int projectID) {
        List<Task> tasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = """
                    SELECT * 
                    FROM tasks 
                    WHERE projectID = ? AND taskCompletionStatus IS FALSE
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
                        rs.getDate(5).toLocalDate(),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getBoolean(8)
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }

    public List<Task> closedTasks(int projectID) {
        List<Task> tasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = """
                    SELECT * 
                    FROM tasks 
                    WHERE projectID = ? AND taskCompletionStatus IS TRUE
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
                        rs.getDate(5).toLocalDate(),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getBoolean(8)
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }

    public List<Task> imminentOpenTasks(int projectID) {
        List<Task> imminentTasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = """
                    SELECT * 
                    FROM tasks 
                    WHERE projectID = ? AND taskCompletionStatus IS FALSE AND taskDueDate BETWEEN current_date AND current_date + INTERVAL 3 DAY
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
                        rs.getDate(5).toLocalDate(),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getBoolean(8)
                );
                imminentTasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return imminentTasks;
    }

    public List<Task> overdueOpenTasks(int projectID) {
        List<Task> overdueTasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = """
                SELECT * 
                FROM tasks 
                WHERE projectID = ? AND taskCompletionStatus AND taskDueDate < current_date
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
                        rs.getDate(5).toLocalDate(),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getBoolean(8)
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

    public void createTask2(Task task, int userID, int projectID) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);

        String sql = "INSERT INTO tasks (projectID, taskName, taskDescription, taskStartDate, taskDueDate, userID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, projectID);
            pstmt.setString(2, task.getTaskName());
            pstmt.setString(3, task.getTaskDescription());
            pstmt.setDate(4, Date.valueOf(task.getTaskStartDate()));
            pstmt.setDate(5, Date.valueOf(task.getTaskDueDate()));
            pstmt.setInt(6, userID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int taskID = rs.getInt(1);

                String criteriaSql = "INSERT INTO taskAcceptCriteria (taskID, taskAcceptCriteriaTEXT, taskStatus) VALUES (?, ?, ?)";
                PreparedStatement criteriaStmt = connection.prepareStatement(criteriaSql);
                for (TaskAcceptCriteria criteria : task.getTaskAcceptCriteria()) {
                    criteriaStmt.setInt(1, taskID);
                    criteriaStmt.setString(2, criteria.getTaskAcceptCriteriaTEXT());
                    criteriaStmt.setBoolean(3, criteria.isTaskStatus());
                    criteriaStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Task findOpenTask(int taskID) {
        String sql = """
            SELECT t.*, tac.criteriaID, tac.taskAcceptCriteriaTEXT, tac.taskStatus
            FROM tasks t
            LEFT JOIN taskAcceptCriteria tac ON t.taskID = tac.taskID
            WHERE t.taskID = ? AND t.taskCompletionStatus IS FALSE
            """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, taskID);
            ResultSet rs = ps.executeQuery();

            Task task = null;
            List<TaskAcceptCriteria> criteriaList = new ArrayList<>();

            while (rs.next()) {
                // Fetch task details if not fetched already
                if (task == null) {
                    task = new Task(
                            rs.getInt("taskID"),
                            rs.getString("taskName"),
                            rs.getString("taskDescription"),
                            rs.getDate("taskStartDate").toLocalDate(),
                            rs.getDate("taskDueDate").toLocalDate(),
                            rs.getInt("projectID"),
                            rs.getInt("userID"),
                            rs.getBoolean("taskCompletionStatus")
                    );
                }

                // Fetch criteria details
                TaskAcceptCriteria criteria = new TaskAcceptCriteria(
                        rs.getInt("criteriaID"),
                        rs.getBoolean("taskStatus"),
                        rs.getString("taskAcceptCriteriaTEXT")
                );
                criteriaList.add(criteria);
            }

            if (task != null) {
                task.setTaskAcceptCriteria(criteriaList);
            }

            return task;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Task findClosedTask(int taskID) {
        String sql = """
            SELECT t.*, tac.criteriaID, tac.taskAcceptCriteriaTEXT, tac.taskStatus
            FROM tasks t
            LEFT JOIN taskAcceptCriteria tac ON t.taskID = tac.taskID
            WHERE t.taskID = ? AND t.taskCompletionStatus IS TRUE
            """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, taskID);
            ResultSet rs = ps.executeQuery();

            Task task = null;
            List<TaskAcceptCriteria> criteriaList = new ArrayList<>();

            while (rs.next()) {
                // Fetch task details if not fetched already
                if (task == null) {
                    task = new Task(
                            rs.getInt("taskID"),
                            rs.getString("taskName"),
                            rs.getString("taskDescription"),
                            rs.getDate("taskStartDate").toLocalDate(),
                            rs.getDate("taskDueDate").toLocalDate(),
                            rs.getInt("projectID"),
                            rs.getInt("userID"),
                            rs.getBoolean("taskCompletionStatus")
                    );
                }

                // Fetch criteria details
                TaskAcceptCriteria criteria = new TaskAcceptCriteria(
                        rs.getInt("criteriaID"),
                        rs.getBoolean("taskStatus"),
                        rs.getString("taskAcceptCriteriaTEXT")
                );
                criteriaList.add(criteria);
            }

            if (task != null) {
                task.setTaskAcceptCriteria(criteriaList);
            }

            return task;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Task findClosedTask2(int taskID) {
        String sql = """
        SELECT *
        FROM tasks 
        WHERE taskID = ? AND taskCompletionStatus IS TRUE
        """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, taskID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                task = new Task(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate(),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getBoolean(8)
                );
                return task;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean updateTask2(Task task, int taskID) {
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


    public void updateTaskAcceptCriteria(int taskID, List<TaskAcceptCriteria> criteriaList) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);

        String sql = "UPDATE taskAcceptCriteria SET taskAcceptCriteriaTEXT = ?, taskStatus = ? WHERE criteriaID = ? AND taskID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (TaskAcceptCriteria criteria : criteriaList) {
                ps.setString(1, criteria.getTaskAcceptCriteriaTEXT());
                ps.setBoolean(2, criteria.isTaskStatus());
                ps.setInt(3, criteria.getCriteriaID());
                ps.setInt(4, taskID);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TaskAcceptCriteria> findTaskAcceptCriteria(int taskID) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);

        String sql = "SELECT criteriaID, taskAcceptCriteriaTEXT, taskStatus FROM taskAcceptCriteria WHERE taskID = ?";
        List<TaskAcceptCriteria> criteriaList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, taskID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int criteriaID = rs.getInt("criteriaID");
                    String taskAcceptCriteriaText = rs.getString("taskAcceptCriteriaTEXT");
                    boolean taskStatus = rs.getBoolean("taskStatus");

                    TaskAcceptCriteria criteria = new TaskAcceptCriteria(criteriaID, taskStatus, taskAcceptCriteriaText);
                    criteriaList.add(criteria);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return criteriaList;
    }

    public String getCriteriaString(int criteriaID) {
        String sql = "SELECT taskAcceptCriteriaTEXT FROM taskAcceptCriteria WHERE criteriaID = ?";
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, criteriaID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("taskAcceptCriteriaTEXT");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if no criteria string found for the given criteria ID
    }

    public boolean closeTask(int taskID) {
        String criteriaSql = "SELECT * FROM taskAcceptCriteria WHERE taskID = ?";
        String updateSql = "UPDATE tasks SET taskCompletionStatus = TRUE WHERE taskID = ?";
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement criteriaPs = connection.prepareStatement(criteriaSql);
             PreparedStatement updatePs = connection.prepareStatement(updateSql)) {
            criteriaPs.setInt(1, taskID);
            ResultSet rs = criteriaPs.executeQuery();
            while (rs.next()) {
                if (!rs.getBoolean("taskStatus")) {
                    return false;
                }
            }
            updatePs.setInt(1, taskID);
            updatePs.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<TaskAcceptCriteria> findTaskAcceptCriteriaByTaskID(int taskID) {
        List<TaskAcceptCriteria> criteriaList = new ArrayList<>();
        String sql = "SELECT * FROM taskAcceptCriteria WHERE taskID = ?";
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, taskID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TaskAcceptCriteria criteria = new TaskAcceptCriteria(
                        rs.getInt("criteriaID"),
                        rs.getInt("taskID"),
                        rs.getBoolean("taskStatus"),
                        rs.getString("taskAcceptCriteriaTEXT")
                );
                criteriaList.add(criteria);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return criteriaList;
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

    public Project findProjectById(int projectID) {
        String sql = """
    SELECT * 
    FROM projects 
    WHERE projectID = ?
    """;
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                project = new Project(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(1),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate(),
                        rs.getDouble(6),
                        rs.getDate(7) != null ? rs.getDate(7).toLocalDate() : null
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return project;
    }

    public double getBudgetSpent(int projectID) {
        Period period = Period.between(project.getProjectStartDate(), LocalDate.now());
        int daysSpent = period.getDays() + period.getMonths() * 30 + period.getYears() * 365;
        double dailyBudgetRate = project.getProjectBudget() / (Period.between(project.getProjectStartDate(), project.getProjectDueDate()).getDays() + 1);
        return daysSpent * dailyBudgetRate;
    }

    public double getBudgetRemaining(int projectID) {
        return project.getProjectBudget() - getBudgetSpent(projectID);
    }
}
