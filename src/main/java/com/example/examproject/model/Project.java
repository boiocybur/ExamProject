package com.example.examproject.model;

import java.time.LocalDate;
import java.util.List;

public class Project {
    private LocalDate startDate;
    private LocalDate endDate;
    private double budget;
    private List<String> tasks;
    private List<String> resources;

    public Project(LocalDate startDate, LocalDate endDate, double budget, List<String> tasks, List<String> resources) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.tasks = tasks;
        this.resources = resources;
    }

    public Project() {
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
