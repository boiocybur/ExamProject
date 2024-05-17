package com.example.examproject.service;

import com.example.examproject.model.Project;
import com.example.examproject.model.ProjectList;
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

    public List<ProjectList> showAllProjectLists() {
        return projectListRepository.showAllProjectLists();
    }

    public List<ProjectList> showProjectList(int userID) {
        return projectListRepository.showProjectList(userID);
    }

    public ProjectList createProjectList(ProjectList projectList) {
        return projectListRepository.createProjectList(projectList);
    }

    public boolean deleteProjectList(int userID) {
        return projectListRepository.deleteProjectList(userID);
    }

    public ProjectList searchToUpdate(int projectListID) {
        return projectListRepository.searchToUpdate(projectListID);
    }

    public Project findProject(int projectID) {
        return projectListRepository.findProject(projectID);
    }

    public Project findProjectIDByProjectName(String projectName) {
        return projectListRepository.findIDBProjectName(projectName);
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
