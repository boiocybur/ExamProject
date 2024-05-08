
package com.example.examproject.service;

import com.example.examproject.model.Project;
import com.example.examproject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }


    public LocalDate getProjectEndDate(int projectId) {
        return projectRepository.showProjectTime(projectId);
    }

    public void showBudget() {
        projectRepository.showBudget();
    }

    public void printProjectTime(int projectId) {
        LocalDate projectEndTime = projectRepository.showProjectTime(projectId);
        if (projectEndTime != null) {
            System.out.println("Project End Time for project " + projectId + ": " + projectEndTime);
        } else {
            System.out.println("No end time found for project " + projectId);
        }
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
    public List<Project> findProjectsByImminentDeadlines() {
        return projectRepository.findProjectsByImminentDeadlines();
    }

    public List<Project> findOverdueProjects() {
        return projectRepository.findOverdueProjects();
    }

    public List<Project> findCompletedProjects() {
        return projectRepository.findCompletedProjects();
    }

    public List<Project> findAllProjects(){
        return projectRepository.findAllProjects();
    }

}