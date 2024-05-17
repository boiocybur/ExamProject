package com.example.examproject.model;


import java.util.List;

public class ProjectList {
    private int projectListID;
    private String projectListName;
    private String projectListDescription;
    private List<Project> projectList;


    public ProjectList(int projectListID, String projectListName, String projectListDescription, List<Project> projectList) {
        this.projectListID = projectListID;
        this.projectListName = projectListName;
        this.projectListDescription = projectListDescription;
        this.projectList = projectList;
    }

    public ProjectList(int projectListID, String projectListName, String projectListDescription) {
        this.projectListID = projectListID;
        this.projectListName = projectListName;
        this.projectListDescription = projectListDescription;
    }

    public ProjectList() {

    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
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

    public String getProjectListDescription() {
        return projectListDescription;
    }

    public void setProjectListDescription(String projectListDescription) {
        this.projectListDescription = projectListDescription;
    }
}
