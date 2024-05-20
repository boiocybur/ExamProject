package com.example.examproject.controller;

import com.example.examproject.model.Project;
import com.example.examproject.service.ProjectListService;
import com.example.examproject.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectListService projectListService;

    public ProjectController(ProjectService projectService, ProjectListService projectListService) {
        this.projectService = projectService;
        this.projectListService = projectListService;
    }

    @GetMapping("/{projectID}/createProject")
    public String createProjectForm(@PathVariable int projectID, Model model) {
        model.addAttribute("projectID", projectID);
        model.addAttribute("projectObject", new Project());
        return "project_create_project";
    }

    @GetMapping("")
    public String defaultDashboard(Model model) {
        model.addAttribute("section", "default");
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String frontPage(Model model, @RequestParam(required = false) Integer userID) {
        if (userID != null) {
            model.addAttribute("projectList", projectListService.showProjectList(userID));
        } else {
            model.addAttribute("projectList", new ArrayList<>());
        }
        return "project_frontpage";
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
        System.out.println("All Projects: " + allProjects.size());
        for (Project project : allProjects) {
            System.out.println("Project: " + project.getProjectName());
        }
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

    @GetMapping("/editBudget/{projectId}")
    public String editBudget(@PathVariable int projectId, Model model) {
        Project project = projectService.findProjectById(projectId);
        model.addAttribute("project", project);
        return "editBudget";
    }

    @PostMapping("/updateBudget")
    public String updateBudget(@ModelAttribute Project project) {
        projectService.updateProjectBudget(project);
        return "redirect:/dashboard/budgetOverview";
    }
}
