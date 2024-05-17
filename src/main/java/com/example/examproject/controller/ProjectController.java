package com.example.examproject.controller;

import com.example.examproject.service.ProjectListService;
import com.example.examproject.service.ProjectService;
import com.example.examproject.model.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectListService projectListService;
    private Project project;



    public ProjectController(ProjectService projectService, ProjectListService projectListService) {
        this.projectService = projectService;
        this.projectListService = projectListService;
        this.project = new Project();
    }

    @GetMapping("")
    public String frontPage(Model model, int userID) {
        model.addAttribute("projectList", projectListService.showProjectList(userID));
        return "project_frontpage";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("imminentProjects", projectService.findProjectsByImminentDeadlines());
        model.addAttribute("overdueProjects", projectService.findOverdueProjects());
        model.addAttribute("completedProjects", projectService.findCompletedProjects());
        model.addAttribute("allProjects", projectService.findAllProjects());
        return "dashboard";
    }
}
