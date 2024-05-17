package com.example.examproject.controller;

import com.example.examproject.model.ProjectList;
import com.example.examproject.model.Project;
import com.example.examproject.service.ProjectService;
import com.example.examproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.examproject.service.ProjectListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("projectList")
public class ProjectListController {

    private Project project;
    private ProjectList projectList;

    private ProjectListService projectListService;
    private ProjectService projectService;
    private UserService userService;

    public ProjectListController(ProjectListService projectListService, ProjectService projectService, UserService userService) {
        this.projectListService = projectListService;
        this.projectService = projectService;
        this.userService = userService;
        this.project = new Project();
        this.projectList = new ProjectList();
    }

    @GetMapping("")
    public String frontpage(Model model, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null) {
            model.addAttribute("userID", userID);
            model.addAttribute("projectList", projectListService.getOpenProjectsCreatedByUser2(userID));
            return "projectList_frontpage";
        } else return "errorPage";
    }

    @GetMapping("/showAllProjectLists")
    public String showAllProjectLists(Model model) {
        model.addAttribute("allProjectLists", projectListService.showAllProjectLists());
        return "placeholder_show_allProjectLists";
    }

    @GetMapping("/{userID}/showProjectList")
    public String showProjects(@PathVariable int userID, Model model, HttpSession session) {
        model.addAttribute("projectList", projectListService.showProjectList(userID));
        model.addAttribute("projectListId", userID);
        return "placeholder_show_projectList";
    }

    @GetMapping("/{userID}/createProjectList")
    public String createProjectListForm(@PathVariable int userID) {
        return "placeholder_create_projectList";
    }

    @PostMapping("/{userID}/createProjectList")
    public String createProjectList(@PathVariable String userID) {
        return "placeholder_create_projectList";
    }

    @GetMapping("/{userID}/deleteProjectList")
    public String deleteProject(@PathVariable("userID") int userID) {
        projectListService.deleteProjectList(userID);
        return "redirect:/projectList_show_projectList";
    }

    @GetMapping("/{projectListID}/updateProjectList")
    public String updateProject(@PathVariable int projectListID, Model model) {
        ProjectList projectList = projectListService.searchToUpdate(projectListID);
        model.addAttribute("projectList", projectList);
        return "projectList_update_projectList";
    }

    @PostMapping("/updateProjectList")
    public String updateProject(@ModelAttribute ProjectList projectList) {
        projectListService.updateProjectList(projectList);
        return "redirect:/projectList_show_projectList";
    }

    @GetMapping("/{userID}/createProject")
    public String createProjectForm(@PathVariable int userID, Model model) {
        model.addAttribute("userID", userID);
        model.addAttribute("projectObject", new Project());
        return "projectList_create_project";
    }

    @PostMapping("/createProject")
    public String createProject(@ModelAttribute("projectObject") Project project, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        projectListService.createProject(project, userID);
        return "redirect:/projectList";
    }


}
