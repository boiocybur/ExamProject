
package com.example.examproject.service;

import com.example.examproject.model.Project;
import com.example.examproject.model.Task;
import com.example.examproject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }




    public List<Task> assignedTasks(int projectID) {
        return projectRepository.assignedTasks(projectID);
    }



    public void createTask(Task task, int userID, int projectID) {
        projectRepository.createTask(task, userID, projectID);
    }

    public List<Task> imminentAssignedTasks(int projectID) {
        return projectRepository.imminentAssignedTasks(projectID);
    }

    public List<Task> overdueAssignedTasks(int projectID) {
        return projectRepository.overdueAssignedTasks(projectID);
    }

    public boolean updateTask(Task task, int taskID) {
        return projectRepository.updateTask(task, taskID);
    }

    public Task findTask(int taskID) {
        return projectRepository.findTask(taskID);
    }

    public boolean deleteTask(int taskID) {
        return projectRepository.deleteTask(taskID);
    }

    public void assignUserToTask(int userID, int taskID) {
        projectRepository.assignTaskToUser(userID, taskID);
    }

    public double getBudgetSpent(int projectID) {
        return projectRepository.getBudgetSpent(projectID);
    }

    public double getBudgetRemaining(int projectID) {
        return projectRepository.getBudgetRemaining(projectID);
    }
  
    public Project findProjectById(int projectID){
        return projectRepository.findProjectById(projectID);
    }
}