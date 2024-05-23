package com.example.examproject.repository;

import com.example.examproject.model.TaskAcceptCriteria;
import com.example.examproject.model.Project;
import com.example.examproject.model.Task;
import com.example.examproject.model.User;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
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


    private Task task;
    private Project project;
    private TaskAcceptCriteria taskAcceptCriteria;
    

    public List<Task> assignedTasks(int projectID) {
        List<Task> tasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = """
        SELECT * 
        FROM tasks 
        WHERE projectID = ?
        ORDER BY taskDueDate DESC
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
                        rs.getDate(5).toLocalDate()
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }

    public List<Task> imminentAssignedTasks(int projectID) {
        List<Task> imminentTasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = """
                    SELECT * 
                    FROM tasks 
                    WHERE projectID = ? AND taskDueDate BETWEEN current_date AND current_date + INTERVAL 3 DAY
                    ORDER BY taskDueDate DESC
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
                        rs.getDate(5).toLocalDate()
                );
                imminentTasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return imminentTasks;
    }

    public List<Task> overdueAssignedTasks(int projectID) {
        List<Task> overdueTasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = """
                SELECT * 
                FROM tasks 
                WHERE projectID = ? AND taskDueDate < current_date
                ORDER BY taskDueDate DESC
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
                        rs.getDate(5).toLocalDate()
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
                    criteriaStmt.setString(2, criteria.getTaskAcceptCriteria());
                    criteriaStmt.setBoolean(3, criteria.isTaskStatus());
                    criteriaStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Task findTask2(int taskID) {
        String sql = """
        SELECT *
        FROM tasks 
        WHERE taskID = ?
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
                        rs.getInt(7)
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
                ps.setString(1, criteria.getTaskAcceptCriteria());
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
        Project project = findProjectById(projectID);
        LocalDate startDate = project.getProjectStartDate();
        LocalDate dueDate = project.getProjectDueDate();
        LocalDate currentDate = LocalDate.now();

        if (startDate == null || dueDate == null) {
            return 0.0;
        }

        long totalDays = java.time.temporal.ChronoUnit.DAYS.between(startDate, dueDate);
        long daysSpent = java.time.temporal.ChronoUnit.DAYS.between(startDate, currentDate);

        // Avoid division by zero
        if (totalDays == 0) {
            return 0.0;
        }

        double dailyBudgetRate = project.getProjectBudget() / totalDays;
        double budgetSpent = daysSpent * dailyBudgetRate;

        // Ensure budget spent does not exceed the total budget
        return Math.min(budgetSpent, project.getProjectBudget());
    }

    public double getBudgetRemaining(int projectID) {
        Project project = findProjectById(projectID);
        double budgetSpent = getBudgetSpent(projectID);
        return project.getProjectBudget() - budgetSpent;
    }



    public int getTimeTotal() {
        Period period = Period.between(project.getProjectStartDate(), project.getProjectDueDate());
        return period.getYears() * 365 + period.getMonths() * 30 + period.getDays();
    }

    public int getTimeSpent() {
        Period period = Period.between(project.getProjectStartDate(), LocalDate.now());
        return period.getYears() * 365 + period.getMonths() * 30 + period.getDays();
    }

    public int getTimeLeft() {
        Period period = Period.between(LocalDate.now(), project.getProjectDueDate());
        return period.getYears() * 365 + period.getMonths() * 30 + period.getDays();
    }

    public Double getTotalEstimatedTime(int projectID) {
        Double totalEstimatedTime = 0.0;
        String sql = """
                SELECT COALESCE(SUM(estimatedHours), 0) 
                AS totalEstTime
                FROM tasks 
                WHERE projectID = ?
                """;
        try {
            Connection con = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, projectID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalEstimatedTime = rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalEstimatedTime;
    }

    public Double getTotalActualTime(int projectID) {
        Double totalActualTime = 0.0;
        Connection con = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
        String sql = "SELECT COALESCE(SUM(actualHours), 0) AS totalActualTime " +
                "FROM tasks WHERE projectID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, projectID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalActualTime = rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalActualTime;
    }

    public Duration getTotalDuration(int projectID) {
        Duration totalDuration = Duration.ZERO;
        try (Connection con = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            String SQL = "SELECT taskStartDate, taskDueDate " +
                    "FROM tasks WHERE projectID = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, projectID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LocalDate startDate = rs.getDate("taskStartDate").toLocalDate();
                LocalDate dueDate = rs.getDate("taskDueDate").toLocalDate();
                totalDuration = totalDuration.plus(Duration.between(startDate.atStartOfDay(), dueDate.atStartOfDay()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalDuration;
    }

    public void removeAssignedTaskToUser(int userID, int taskID) {
        try {
            Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
            String query = "DELETE FROM tasksanduser WHERE taskID=? AND userID=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, taskID);
            preparedStatement.setInt(2, userID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findAssignedUsersByTaskID(int taskID) {
        List<User> assignedUsers = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection(dbUrl, dbUserName, dbPassword);
            String query = "SELECT * FROM users WHERE userID IN \n" +
            "(SELECT userID FROM tasksanduser WHERE taskID = ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, taskID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userID"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("userPassword"));
                user.setUserEmail(resultSet.getString("userEmail"));
                user.setUserRank(resultSet.getString("userRank"));
                assignedUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return assignedUsers;
    }
}
