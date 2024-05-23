package com.example.examproject.model;

public class TaskAcceptCriteria {
    private int taskID;
    private int criteriaID;
    private boolean taskStatus;
    private String taskAcceptCriteria;

    public TaskAcceptCriteria(int criteriaID, boolean taskStatus, String taskAcceptCriteria) {
        this.criteriaID = criteriaID;
        this.taskStatus = taskStatus;
        this.taskAcceptCriteria = taskAcceptCriteria;
    }

    public TaskAcceptCriteria() {
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getCriteriaID() {
        return criteriaID;
    }

    public void setCriteriaID(int criteriaID) {
        this.criteriaID = criteriaID;
    }

    public boolean isTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskAcceptCriteria() {
        return taskAcceptCriteria;
    }

    public void setTaskAcceptCriteria(String taskAcceptCriteria) {
        this.taskAcceptCriteria = taskAcceptCriteria;
    }
}
