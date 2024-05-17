package com.example.examproject.service;

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

    public List<ProjectList> showProjectList(int projectListId) {
        return projectListRepository.showProjectList(projectListId);
    }

    public ProjectList createProjectList(ProjectList projectList) {
        return projectListRepository.createProjectList(projectList);
    }

    public boolean deleteProjectList(int projectListID) {
        return projectListRepository.deleteProjectlist(projectListID);
    }

    public ProjectList searchToUpdate(int projectListID) {
        return projectListRepository.searchToUpdate(projectListID);
    }

    public boolean updateProjectList(ProjectList projectList) {
        return projectListRepository.updateProjectList(projectList);
    }
}
