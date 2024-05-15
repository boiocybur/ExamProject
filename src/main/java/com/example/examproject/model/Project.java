package com.example.examproject.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String projectName;
    private String projectDescription;
    private int projectID;
    private LocalDate projectStartDate;
    private LocalDate dueDate;
    private double projectBudget;
    private List<String> projectTasks;
    private LocalDate completionDate;
    private double budgetSpent;

    public Project(String projectName, String projectDescription, LocalDate projectStartDate, LocalDate dueDate, double projectBudget, List<String> projectTasks, LocalDate completionDate, double budgetSpent) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectStartDate = projectStartDate;
        this.dueDate = dueDate;

        this.projectBudget = projectBudget;
        this.projectTasks = projectTasks != null ? projectTasks : new ArrayList<>();
        this.completionDate = completionDate;
        this.budgetSpent = budgetSpent;
    }

    public Project() {
    }

    public Project(String projectName) {
        this.projectName = projectName;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate projectEndDate) {
        this.dueDate = projectEndDate;

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

    public double getBudgetSpent() {
        return budgetSpent;
    }

    public void setBudgetSpent(double budgetSpent) {
        this.budgetSpent = budgetSpent;
    }

    public String getBudgetStatus() {
        if (projectBudget > 0) {
            return String.format("%.2f%%", (budgetSpent / projectBudget) * 100);
        }
        return "N/A";
    }
}

