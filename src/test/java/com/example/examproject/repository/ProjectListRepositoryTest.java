package com.example.examproject.repository;

import com.example.examproject.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@ActiveProfiles("h2")
class ProjectListRepositoryTest {
    @Autowired
    ProjectListRepository projectListRepository;
    @Test
    void updateProject() {
        Project project = new Project("Project D", "Description D", 3, LocalDate.now(), LocalDate.now().plusDays(30), 4000.0);
        projectListRepository.createProject(project, 1);

        Project updatedProject = new Project("Updated Project D", "Updated Description D", 3, LocalDate.now(), LocalDate.now().plusDays(35), 4500.0);
        boolean actualResult = projectListRepository.updateProject(updatedProject);
        assertTrue(actualResult);

        Project foundProject = projectListRepository.findProjectNoCompletionDate(3);
        assertNotNull(foundProject);
        assertEquals("Updated Project D", foundProject.getProjectName());
        assertEquals(4500.0, foundProject.getProjectBudget());
    }
    @Test
    void findProjectNoCompletionDate() {
        Project foundProject = projectListRepository.findProjectNoCompletionDate(2);
        assertNotNull(foundProject);
        assertEquals("Marketing Launch", foundProject.getProjectName());
    }

    @Test
    void getOpenProjectsCreatedByUser() {
        List<Project> openProjects = projectListRepository.getOpenProjectsCreatedByUser(1);
        assertEquals(3, openProjects.size());
    }
    @Test
    void getClosedProjectsCreatedByUser() {
        List<Project> closedProjects = projectListRepository.getClosedProjectsCreatedByUser(1);
        assertEquals(1, closedProjects.size());
    }
}