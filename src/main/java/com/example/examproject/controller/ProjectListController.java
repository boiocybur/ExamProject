package com.example.examproject.controller;

import com.example.examproject.model.Project;
import com.example.examproject.service.ProjectService;
import com.example.examproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.examproject.service.ProjectListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("projectList")
public class ProjectListController {

    private Project project;

    private ProjectListService projectListService;
    private ProjectService projectService;
    private UserService userService;

    public ProjectListController(ProjectListService projectListService, ProjectService projectService, UserService userService) {
        this.projectListService = projectListService;
        this.projectService = projectService;
        this.userService = userService;
        this.project = new Project();
    }

    @GetMapping("")
    public String frontpage(Model model, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null) {
            model.addAttribute("userID", userID);
            model.addAttribute("projectList", projectListService.getOpenProjectsCreatedByUser(userID));
            return "projectList_frontpage";
        } else return "errorPage";
    }


    @GetMapping("/{userID}/createProject")
    public String createProjectForm(@PathVariable int userID, Model model, RedirectAttributes redirectAttributes) {
        if (userService.isProjectManager(userID) || userService.isAdmin(userID)) {
            model.addAttribute("userID", userID);
            model.addAttribute("projectObject", new Project());
            return "projectList_create_project";
        }
        else {
            redirectAttributes.addFlashAttribute("error", "You do not have permission to create projects.");
            return "redirect:/projectList";
        }
    }

    @PostMapping("/createProject")
    public String createProject(@ModelAttribute("projectObject") Project project, HttpSession session) {
        Integer loggedInUserId = (Integer) session.getAttribute("userID");
        if (loggedInUserId != null && userService.isProjectManager(loggedInUserId) || loggedInUserId != null && userService.isAdmin(loggedInUserId)) {
            projectListService.createProject(project, loggedInUserId);
                return "redirect:/projectList";
            } else {
                return "redirect:/projectList";
            }
    }

    @GetMapping("/{projectID}/updateProject")
    public String updateProjectForm(@PathVariable int projectID, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Integer loggedInUserId = (Integer) session.getAttribute("userID");
        if(loggedInUserId != null && userService.isProjectManager(loggedInUserId) || loggedInUserId != null && userService.isAdmin(loggedInUserId)) {
            Project project = projectListService.findProjectNoCompletionDate(projectID);
            model.addAttribute("projectID", projectID);
            model.addAttribute("project", project);
            return "projectList_update_project";
        }
        else {
            redirectAttributes.addFlashAttribute("error", "You do not have permission to edit projects.");
            return "redirect:/projectList";
        }
    }

    @PostMapping("/updateProject")
    public String updateProject(@ModelAttribute Project project, HttpSession session) {
        Integer loggedInUserId = (Integer) session.getAttribute("userID");
        if(loggedInUserId != null && userService.isProjectManager(loggedInUserId) || loggedInUserId != null && userService.isAdmin(loggedInUserId)) {
            projectListService.updateProject(project);
            return "redirect:/projectList";
        }
        else {
            return "redirect:/projectList";
        }
    }

}
