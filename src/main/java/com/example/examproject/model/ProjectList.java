package com.example.examproject.model;


import java.util.List;

public class ProjectList {


    private int projectListID;
    private String projectListName;
    private List<Project> projectList;

    public ProjectList(int projectListID, String projectListName) {
        this.projectListID = projectListID;
        this.projectListName = projectListName;
    }

    public ProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public ProjectList() {
    }

    public int getProjectListID() {
        return projectListID;
    }

    public void setProjectListID(int projectListID) {
        this.projectListID = projectListID;
    }

    public String getProjectListName() {
        return projectListName;
    }

    public void setProjectListName(String projectListName) {
        this.projectListName = projectListName;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
}
