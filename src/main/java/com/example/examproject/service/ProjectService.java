
package com.example.examproject.service;

import com.example.examproject.repository.ProjectRepository;
import com.example.examproject.model.Project;
import org.springframework.stereotype.Service;
import java.util.List;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public void showBudget() {
        projectRepository.showBudget();
    }

    public void showProjectTime() {
        projectRepository.showProjectTime();
    }

    public void showUnfinishedTasks() {
        projectRepository.showUnfinishedTasks();
    }

    public void showFinishedTasks() {
        projectRepository.showFinishedTasks();
    }

    public void showOverdueTasks() {
        projectRepository.showOverdueTasks();
    }

    public void showResources() {
        projectRepository.showResources();
    }

    public void attachResourcesToTask() {
        projectRepository.attachResourcesToTask();
    }

    public void removeResourceFromTask() {
        projectRepository.removeResourceFromTask();
    }
}