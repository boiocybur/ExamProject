package com.example.examproject.controller;

import com.example.examproject.model.Project;
import com.example.examproject.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("")
public class ProjectController {
    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/imminent")
    public ResponseEntity<List<Project>> getImminentProjects() {
        List<Project> projects = projectService.findProjectsByImminentDeadlines();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Project>> getOverdueProjects() {
        List<Project> projects = projectService.findOverdueProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Project>> getCompletedProjects() {
        List<Project> projects = projectService.findCompletedProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects(){
        List<Project> projects = projectService.findAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "dashboard-task";
    }

}
