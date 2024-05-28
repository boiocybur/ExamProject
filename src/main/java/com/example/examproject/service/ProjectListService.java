package com.example.examproject.service;

import com.example.examproject.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.examproject.repository.ProjectListRepository;

import java.util.List;

@Service
public class ProjectListService {


    private ProjectListRepository projectListRepository;

    @Autowired
    public ProjectListService(ProjectListRepository projectListRepository) {
        this.projectListRepository = projectListRepository;
    }


    public Project findProjectNoCompletionDate(int projectID) {
        return projectListRepository.findProjectNoCompletionDate(projectID);
    }

    public Project findProjectWithCompletionDate(int projectID) {
        return projectListRepository.findProjectWithCompletionDate(projectID);
    }

    public Project findProjectIDByProjectName(String projectName) {
        return projectListRepository.findIDByProjectName(projectName);
    }

    public boolean updateProject(Project project) {
        return projectListRepository.updateProject(project);
    }

    public void createProject(Project project, int userID) {
        projectListRepository.createProject(project, userID);
    }

    public List<Project> getOpenProjectsCreatedByUser(int userID) {
        return projectListRepository.getOpenProjectsCreatedByUser(userID);
    }

    public List<Project> getClosedProjectsCreatedByUser(int userID) {
        return projectListRepository.getClosedProjectsCreatedByUser(userID);
    }
}
