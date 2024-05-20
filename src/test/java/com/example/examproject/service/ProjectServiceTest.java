/*package com.example.examproject;

import com.example.examproject.model.Project;
import com.example.examproject.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    public void testFindAllProjects() {
            List<Project> projects = projectService.findAllProjects();
        assertFalse(projects.isEmpty(), "Projects list should not be empty");
        for (Project project : projects) {
            System.out.println("Project Name: " + project.getProjectName());
        }
    }
}*/
