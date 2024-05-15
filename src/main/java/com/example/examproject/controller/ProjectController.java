package com.example.examproject.controller;

import com.example.examproject.model.Project;
import com.example.examproject.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String defaultDashboard(Model model) {
        model.addAttribute("section", "default");
        return "dashboard";
    }

    @GetMapping("/imminentProjects")
    public String imminentProjects(Model model) {
        List<Project> imminentProjects = projectService.findProjectsByImminentDeadlines();
        model.addAttribute("imminentProjects", imminentProjects);
        return "imminentProjects";
    }

    @GetMapping("/overdueProjects")
    public String overdueProjects(Model model) {
        List<Project> overdueProjects = projectService.findOverdueProjects();
        model.addAttribute("overdueProjects", overdueProjects);
        return "overdueProjects";
    }

    @GetMapping("/allProjects")
    public String allProjects(Model model) {
        List<Project> allProjects = projectService.findAllProjects();
        model.addAttribute("allProjects", allProjects);
        return "allProjects";
    }

    @GetMapping("/completedProjects")
    public String completedProjects(Model model) {
        List<Project> completedProjects = projectService.findCompletedProjects();
        model.addAttribute("completedProjects", completedProjects);
        return "completedProjects";
    }

    @GetMapping("/budgetOverview")
    public String budgetOverview(Model model) {
        List<Project> allProjects = projectService.findAllProjects();
        model.addAttribute("allProjects", allProjects);
        return "budgetOverview";
    }
}
