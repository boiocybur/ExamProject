package com.example.examproject.controller;

import com.example.examproject.model.Project;
import com.example.examproject.model.Task;
import com.example.examproject.model.TaskAcceptCriteria;
import com.example.examproject.service.ProjectListService;
import com.example.examproject.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectListService projectListService;
    private final Task task;


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
    public String frontPage(@PathVariable int projectID, Model model, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID != null) {
            model.addAttribute("projectID", projectID);
            model.addAttribute("userID", userID);
            model.addAttribute("projectObject", projectListService.findProjectWithCompletionDate(projectID));
            model.addAttribute("taskObject", projectService.assignedTasks(projectID));


            model.addAttribute("assignedTasks", projectService.assignedTasks(projectID));
            model.addAttribute("imminentTasks", projectService.imminentAssignedTasks(projectID));
            model.addAttribute("overdueTasks", projectService.overdueAssignedTasks(projectID));
            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/{projectID}/tasks")
    public String tasks(@PathVariable int projectID, Model model, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        model.addAttribute("userID", userID);
        model.addAttribute("allTasksObject", projectService.assignedTasks(projectID));
        model.addAttribute("imminentTasksObject", projectService.imminentAssignedTasks(projectID));
        model.addAttribute("overdueTasksObject", projectService.overdueAssignedTasks(projectID));
        return "project_tasks";
    }

    @GetMapping("/{projectID}/{userID}/createTask")
    public String createTaskForm(@PathVariable int projectID, @PathVariable int userID, Model model) {
        model.addAttribute("projectID", projectID);
        model.addAttribute("userID", userID);
        model.addAttribute("taskObject", new Task());
        return "project_create_task";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute("taskObject") Task task, @ModelAttribute("projectID") int projectID, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        projectService.createTask2(task, userID, projectID);
        return "redirect:/project/" + projectID + "/tasks";
    }

    @GetMapping("/{projectID}/{taskID}/updateTask")
    public String updateProjectForm(@PathVariable int projectID, @PathVariable int taskID, Model model) {
        Task task = projectService.findTask2(taskID);
        model.addAttribute("projectID", projectID);
        model.addAttribute("taskID", taskID);
        model.addAttribute("taskObject", task);
        return "project_update_task";

    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute("taskObject") Task task, @ModelAttribute("taskID") int taskID, @ModelAttribute("projectID") int projectID) {
        projectService.updateTask2(task, taskID);
        return "redirect:/project/" + projectID + "/tasks";
    }

    @PostMapping("/{projectID}/{taskID}/deleteTask")
    public String deleteTask(@PathVariable int taskID, @PathVariable int projectID) {
        projectService.deleteTask(taskID);
        return "redirect:/project/" + projectID + "/tasks";
    }

    @GetMapping("/{projectID}/{taskID}/taskDetails")
    public String viewTaskDetails(@PathVariable int projectID, @PathVariable int taskID, Model model) {
        Task task = projectService.findTask2(taskID);
        List<TaskAcceptCriteria> taskAcceptCriteria = projectService.findTaskAcceptCriteria(taskID);

        model.addAttribute("projectID", projectID);
        model.addAttribute("taskObject", task);
        model.addAttribute("taskAcceptCriteria", taskAcceptCriteria);
        return "project_task_details";
    }

    @PostMapping("/updateTaskCriteria")
    public String updateCriteria(@RequestParam("taskID") int taskID,
                                 @RequestParam("projectID") int projectID,
                                 @RequestParam("criteriaID") List<Integer> criteriaIDs,
                                 @RequestParam(value = "criteriaStatus", required = false) List<Integer> criteriaStatus,
                                 RedirectAttributes redirectAttributes) {

        System.out.println("1");

        System.out.println("Updating criteria for taskID: " + taskID + " in projectID: " + projectID);
        System.out.println("Received criteria IDs: " + criteriaIDs);
        System.out.println("Received criteria status: " + criteriaStatus);

        List<TaskAcceptCriteria> criteriaList = new ArrayList<>();

        for (Integer criteriaID : criteriaIDs) {
            boolean taskStatus = criteriaStatus != null && criteriaStatus.contains(criteriaID);
            String criteriaString = projectService.getCriteriaString(criteriaID);
            TaskAcceptCriteria criteria = new TaskAcceptCriteria(criteriaID, taskStatus, criteriaString);
            criteriaList.add(criteria);

            System.out.println("Criteria ID: " + criteriaID + " Status: " + taskStatus);
        }

        System.out.println("Criteria List to be updated: " + criteriaList);

        projectService.updateTaskAcceptCriteria(taskID, criteriaList);

        redirectAttributes.addFlashAttribute("successMessage", "Criteria updated successfully!");
        return "redirect:/project/" + projectID + "/" + taskID + "/taskDetails";
    }


}
