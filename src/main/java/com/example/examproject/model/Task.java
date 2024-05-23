package com.example.examproject.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Task {

    private int taskID;
    private String taskName;
    private String taskDescription;
    private LocalDate taskStartDate;
    private LocalDate taskDueDate;
    private int projectID;
    private int userID;
    private List<TaskAcceptCriteria> taskAcceptCriteria;

    public Task(int taskID, String taskName, String taskDescription, LocalDate taskStartDate, LocalDate taskDueDate) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStartDate = taskStartDate;
        this.taskDueDate = taskDueDate;
    }

    public Task(int taskID, String taskName, String taskDescription, LocalDate taskStartDate, LocalDate taskDueDate, int projectID, int userID) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStartDate = taskStartDate;
        this.taskDueDate = taskDueDate;
        this.projectID = projectID;
        this.userID = userID;
    }

    public Task() {
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(LocalDate taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public LocalDate getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(LocalDate taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<TaskAcceptCriteria> getTaskAcceptCriteria() {
        return taskAcceptCriteria;
    }

    public void setTaskAcceptCriteria(List<TaskAcceptCriteria> taskAcceptCriteria) {
        this.taskAcceptCriteria = taskAcceptCriteria;
    }
}