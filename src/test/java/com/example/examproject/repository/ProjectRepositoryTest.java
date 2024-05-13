package com.example.examproject.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class ProjectRepositoryTest {

    @InjectMocks
    private ProjectRepository projectRepository;

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);  // Simulate at least one result returned
        mockProject();  // Assume this method mocks resultSet getting project data
    }

    @Test
    void findProjectsByImminentDeadlines() throws SQLException {
        LocalDate today = LocalDate.now();
        LocalDate imminentDeadline = today.plusDays(3);
        String expectedSql = "SELECT * FROM projects WHERE due_date BETWEEN ? AND ? AND completion_date IS NULL";

        projectRepository.findProjectsByImminentDeadlines();

        verify(preparedStatement).setDate(1, Date.valueOf(today));
        verify(preparedStatement).setDate(2, Date.valueOf(imminentDeadline));
        verify(preparedStatement).executeQuery();
        verify(connection).prepareStatement(expectedSql);
    }

    @Test
    void findCompletedProjects() throws SQLException {
        String expectedSql = "SELECT * FROM projects WHERE completion_date IS NOT NULL";

        projectRepository.findCompletedProjects();

        verify(preparedStatement).executeQuery();
        verify(connection).prepareStatement(expectedSql);
    }

    @Test
    void findOverdueProjects() throws SQLException {
        LocalDate today = LocalDate.now();
        String expectedSql = "SELECT * FROM projects WHERE due_date < ? AND completion_date IS NULL";

        projectRepository.findOverdueProjects();

        verify(preparedStatement).setDate(1, Date.valueOf(today));
        verify(preparedStatement).executeQuery();
        verify(connection).prepareStatement(expectedSql);
    }

    @Test
    void findAllProjects() throws SQLException {
        String expectedSql = "SELECT * FROM projects";

        projectRepository.findAllProjects();

        verify(preparedStatement).executeQuery();
        verify(connection).prepareStatement(expectedSql);
    }

    private void mockProject() throws SQLException {
        when(resultSet.getInt("project_id")).thenReturn(1);
        when(resultSet.getString("project_name")).thenReturn("Sample Project");
        when(resultSet.getString("project_description")).thenReturn("Sample Description");
        when(resultSet.getDate("project_start_date")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getDouble("project_budget")).thenReturn(10000.0);
        when(resultSet.getDate("due_date")).thenReturn(Date.valueOf(LocalDate.now().plusDays(5)));
        when(resultSet.getDate("completion_date")).thenReturn(Date.valueOf(LocalDate.now()));
    }
}
