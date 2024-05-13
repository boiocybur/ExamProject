package com.example.examproject.controller;

import com.example.examproject.service.ProjectService;
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

    @GetMapping("dashboard")
    public String dashboard(Model model) {
        model.addAttribute("imminentProjects", projectService.findProjectsByImminentDeadlines());
        model.addAttribute("overdueProjects", projectService.findOverdueProjects());
        model.addAttribute("completedProjects", projectService.findCompletedProjects());
        model.addAttribute("allProjects", projectService.findAllProjects());
        return "dashboard";
    }


}
