package com.example.examproject.controller;

import com.example.examproject.model.Project;
import com.example.examproject.model.Task;
import com.example.examproject.service.ProjectListService;
import com.example.examproject.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectListService projectListService;

    public ProjectController(ProjectService projectService, ProjectListService projectListService) {
        this.projectService = projectService;
        this.projectListService = projectListService;
        this.task = new Task();
    }

    @GetMapping("")
    public String defaultDashboard(Model model) {
        model.addAttribute("section", "default");
        return "dashboard";
    }

    @GetMapping("/{projectID}/dashboard")
    public String frontPage(@PathVariable ("projectID") int projectID, Model model, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null) {
            model.addAttribute("project", projectListService.findProjectWithCompletionDate(projectID));
        }
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
