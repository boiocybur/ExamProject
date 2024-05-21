package com.example.examproject.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String projectName;
    private String projectDescription;
    private int projectID;
    private LocalDate projectStartDate;
    private LocalDate projectDueDate;
    private double projectBudget;
    private List<Task> projectTasks;
    private LocalDate completionDate;

    public Project(String projectName, String projectDescription, LocalDate projectStartDate, LocalDate dueDate, double projectBudget, List<Task> projectTasks, LocalDate completionDate) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectStartDate = projectStartDate;
        this.projectDueDate = dueDate;
        this.projectBudget = projectBudget;
        this.projectTasks = projectTasks != null ? projectTasks : new ArrayList<>();
        this.completionDate = completionDate;
    }

    public Project(String projectName, String projectDescription, int projectID) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectID = projectID;
    }

    public Project(String projectName, String projectDescription, int projectID, LocalDate projectStartDate, LocalDate dueDate, double projectBudget) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectID = projectID;
        this.projectStartDate = projectStartDate;
        this.projectDueDate = dueDate;
        this.projectBudget = projectBudget;
    }

    public Project(String projectName, String projectDescription, int projectID, LocalDate projectStartDate, LocalDate projectDueDate, double projectBudget, LocalDate completionDate) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectID = projectID;
        this.projectStartDate = projectStartDate;
        this.projectDueDate = projectDueDate;
        this.projectBudget = projectBudget;
        this.completionDate = completionDate;
    }
    public Project(int projectID){
        this.projectID = projectID;
    }
    public Project() {
    }

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectDueDate(LocalDate dueDate) {
        this.projectDueDate = dueDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public LocalDate getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(LocalDate projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public LocalDate getProjectDueDate() {
        return projectDueDate;
    }

    public double getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(double projectBudget) {
        this.projectBudget = projectBudget;
    }

    public List<Task> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectTasks(List<Task> projectTasks) {
        this.projectTasks = projectTasks;
    }
}

