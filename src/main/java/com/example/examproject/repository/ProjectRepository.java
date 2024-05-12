package com.example.examproject.repository;

import com.example.examproject.model.Project;
import com.example.examproject.model.ProjectList;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {
    @Value("${spring.datasource.url}")
    String db_url;
    @Value("${spring.datasource.username}")
    String uid;
    @Value("${spring.datasource.password}")
    String pwd;

    private Project project;

    public void showBudget() {
    }

    public void showProjectTime() {
    }

    public void showUnfinishedTasks() {
    }

    public void showFinishedTasks() {
    }

    public void showOverdueTasks() {
    }

    public void showResources() {
    }

    public void attachResourcesToTask() {
    }

    public void removeResourceFromTask() {
    }
}