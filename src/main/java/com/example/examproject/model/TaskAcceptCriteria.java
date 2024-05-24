package com.example.examproject.model;

public class TaskAcceptCriteria {
    private int taskID;
    private int criteriaID;
    private boolean taskStatus;
    private String taskAcceptCriteriaTEXT;

    public TaskAcceptCriteria(int taskID, int criteriaID, boolean taskStatus, String taskAcceptCriteriaTEXT) {
        this.taskID = taskID;
        this.criteriaID = criteriaID;
        this.taskStatus = taskStatus;
        this.taskAcceptCriteriaTEXT = taskAcceptCriteriaTEXT;
    }

    public TaskAcceptCriteria() {
    }

    public TaskAcceptCriteria(int criteriaID, boolean taskStatus, String taskAcceptCriteriaTEXT) {
        this.criteriaID = criteriaID;
        this.taskStatus = taskStatus;
        this.taskAcceptCriteriaTEXT = taskAcceptCriteriaTEXT;
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

    public String getTaskAcceptCriteriaTEXT() {
        return taskAcceptCriteriaTEXT;
    }

    public void setTaskAcceptCriteriaTEXT(String taskAcceptCriteriaTEXT) {
        this.taskAcceptCriteriaTEXT = taskAcceptCriteriaTEXT;
    }

    @Override
    public String toString() {
        return "TaskAcceptCriteria{" +
                "taskID=" + taskID +
                ", criteriaID=" + criteriaID +
                ", taskStatus=" + taskStatus +
                ", taskAcceptCriteriaTEXT='" + taskAcceptCriteriaTEXT + '\'' +
                '}';
    }
}
