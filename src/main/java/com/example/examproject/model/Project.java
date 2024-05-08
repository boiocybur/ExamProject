package com.example.examproject.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String projectName;
    private String projectDescription;
    private int projectId;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private double projectBudget;
    private List<String> projectTasks;
    private LocalDate completionDate;

    public Project(String projectName, String projectDescription, LocalDate projectStartDate, LocalDate projectEndDate, double projectBudget, List<String> projectTasks, LocalDate completionDate) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectBudget = projectBudget;
        this.projectTasks = projectTasks != null ? projectTasks : new ArrayList<>();
        this.completionDate = completionDate;
    }

    public Project() {
    }

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectEndDate(LocalDate projectEndDate) {
        this.projectEndDate = projectEndDate;
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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public LocalDate getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(LocalDate projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public LocalDate getProjectEndDate() {
        return projectEndDate;
    }

    public void setDueDate(LocalDate projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public double getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(double projectBudget) {
        this.projectBudget = projectBudget;
    }

    public List<String> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectTasks(List<String> projectTasks) {
        this.projectTasks = projectTasks;
    }
}

