package com.example.examproject.controller;

import com.example.examproject.model.ProjectList;
import com.example.examproject.model.Project;
import com.example.examproject.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.examproject.service.ProjectListService;

@Controller
@RequestMapping("projectList")
public class ProjectListController {

    private Project project;
    private ProjectList projectList;

    private ProjectListService projectListService;
    private ProjectService projectService;

    public ProjectListController(ProjectListService projectListService, ProjectService projectService) {
        this.projectListService = projectListService;
        this.projectService = projectService;
        this.project = new Project();
        this.projectList = new ProjectList();
    }

    @GetMapping("")
    public String frontpage() {
        return "projectList_frontpage";
    }

    @GetMapping("/showAllProjectLists")
    public String showAllProjectLists(Model model) {
        model.addAttribute("allProjectLists", projectListService.showAllProjectLists());
        return "placeholder_show_allProjectLists";
    }

    @GetMapping("/{projectListId}/showProjectList")
    public String showProjects(@PathVariable int projectListId, Model model) {
        model.addAttribute("projectList", projectListService.showProjectList(projectListId));
        model.addAttribute("projectListId", projectListId);
        return "placeholder_show_projectList";
    }

    @GetMapping("/createProjectList")
    public String createProjectListForm(Model model) {
        model.addAttribute("projectListObject", new Project());
        return "projectList_create_projectList";
    }

    @PostMapping("/createProjectList")
    public String createProjectList(@ModelAttribute ("projectListObject") ProjectList projectList) {
        projectListService.createProjectList(projectList);
        return "redirect:/projectList_show_projectList";
    }

    @GetMapping("/{projectListID}/deleteProjectList")
    public String deleteProject(@PathVariable("projectListID") int projectListID) {
        projectListService.deleteProjectList(projectListID);
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
}
