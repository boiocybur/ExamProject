
package com.example.examproject.service;

import com.example.examproject.model.Project;
import com.example.examproject.model.Task;
import com.example.examproject.model.User;
import com.example.examproject.model.TaskAcceptCriteria;
import com.example.examproject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public List<Task> openTasks(int projectID) {
        return projectRepository.openTasks(projectID);
    }

    public List<Task> closedTasks(int projectID) {
        return projectRepository.closedTasks(projectID);
    }

    public boolean closeTask(int taskID) {
        return projectRepository.closeTask(taskID);
    }
  
    public void createTask(Task task, int userID, int projectID) {
        projectRepository.createTask(task, userID, projectID);
    }

    public List<Task> imminentOpenTasks(int projectID) {
        return projectRepository.imminentOpenTasks(projectID);
    }

    public List<Task> overdueOpenTasks(int projectID) {
        return projectRepository.overdueOpenTasks(projectID);
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

    public void createTask2(Task task, int userID, int projectID) {
        projectRepository.createTask2(task, userID, projectID);
    }

    public void updateTask2(Task task, int taskID) {
        projectRepository.updateTask2(task, taskID);
    }

    public Task findOpenTask(int taskID) {
        return projectRepository.findOpenTask(taskID);
    }

    public Task findClosedTask(int taskID) {
        return projectRepository.findClosedTask(taskID);
    }

    public void updateTaskAcceptCriteria(int taskID, List<TaskAcceptCriteria> taskAcceptCriteria) {
        projectRepository.updateTaskAcceptCriteria(taskID, taskAcceptCriteria);
    }

    public List<TaskAcceptCriteria> findTaskAcceptCriteria(int taskID) {
        return projectRepository.findTaskAcceptCriteria(taskID);
    }

    public String getCriteriaString(int criteriaID) {
        return projectRepository.getCriteriaString(criteriaID);
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

    public Project findProjectById(int projectID) {
        return projectRepository.findProjectById(projectID);
    }

    public int getTimeSpent() {
        return projectRepository.getTimeSpent();
    }

    public int getTimeTotal() {
        return projectRepository.getTimeTotal();
    }

    public int getTimeLeft() {
        return projectRepository.getTimeLeft();
    }

    public void removeAssignedUserToTask(int userID, int taskID) {
        projectRepository.removeAssignedTaskToUser(userID, taskID);
    }

    public List<User> getAssignedUsers(int taskID) {
        return projectRepository.findAssignedUsersByTaskID(taskID);
    }

    public Double getTotalEstimatedTime(int projectID) {
        return projectRepository.getTotalEstimatedTime(projectID);
    }

    public Double getTotalActualTime(int projectID) {
        return projectRepository.getTotalActualTime(projectID);
    }

    public Duration getTotalDuration(int projectID) {
        return projectRepository.getTotalDuration(projectID);
    }
}
