package com.example.examproject.controller;

import com.example.examproject.model.Project;
import com.example.examproject.model.Task;
import com.example.examproject.model.User;
import com.example.examproject.model.TaskAcceptCriteria;
import com.example.examproject.service.ProjectListService;
import com.example.examproject.service.ProjectService;
import com.example.examproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectListService projectListService;
    private UserService userService;
    private final Task task;
    private final TaskAcceptCriteria taskAcceptCriteria;


    public ProjectController(ProjectService projectService, ProjectListService projectListService, UserService userService) {
        this.projectService = projectService;
        this.projectListService = projectListService;
        this.userService = userService;
        this.task = new Task();
        this.taskAcceptCriteria = new TaskAcceptCriteria();
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
            model.addAttribute("taskObject", projectService.openTasks(projectID));


            model.addAttribute("assignedTasks", projectService.openTasks(projectID));
            model.addAttribute("imminentTasks", projectService.imminentOpenTasks(projectID));
            model.addAttribute("overdueTasks", projectService.overdueOpenTasks(projectID));
            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/{projectID}/tasks")
    public String tasks(@PathVariable int projectID, Model model, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        model.addAttribute("userID", userID);
        model.addAttribute("projectID", projectID);
        model.addAttribute("openTasksObject", projectService.openTasks(projectID));
        model.addAttribute("closedTasksObject", projectService.closedTasks(projectID));
        model.addAttribute("imminentOpenTasksObject", projectService.imminentOpenTasks(projectID));
        model.addAttribute("overdueOpenTasksObject", projectService.overdueOpenTasks(projectID));
        return "project_tasks";
    }

    @PostMapping("/closeTask")
    public String closeTask(@RequestParam int taskID, @RequestParam int projectID, Model model) {
        boolean canClose = projectService.closeTask(taskID);
        if (canClose) {
            return "redirect:/project/" + projectID + "/tasks";
        } else {
            Task task = projectService.findOpenTask(taskID);
            model.addAttribute("openTaskObject", task);
            model.addAttribute("errorMessage", "All acceptance criteria must be met to close the task, remember to update.");
            return "project_task_details";
        }
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
    public String updateTaskForm(@PathVariable("taskID") int taskID,
                                 @PathVariable ("projectID") int projectID,
                                 Model model) {
        Task task = projectService.findOpenTask(taskID);
        List<TaskAcceptCriteria> criteriaList = projectService.findTaskAcceptCriteria(taskID);
        model.addAttribute("taskObject", task);
        model.addAttribute("projectID", projectID);
        model.addAttribute("criteriaList", criteriaList);
        System.out.println(criteriaList);
        System.out.println(projectID);
        System.out.println(task);
        return "project_update_task";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute("taskObject") Task task,
                             @ModelAttribute("taskID") int taskID,
                             @ModelAttribute("projectID") int projectID,
                             @ModelAttribute("criteriaList") List<String> criteriaListText,
                             @ModelAttribute("taskStatus") List<Boolean> taskStatusList) {
        List<TaskAcceptCriteria> criteriaList = new ArrayList<>();
        for (int i = 0; i < criteriaListText.size(); i++) {
            TaskAcceptCriteria criteria = new TaskAcceptCriteria();
            criteria.setTaskAcceptCriteriaTEXT(criteriaListText.get(i));
            criteria.setTaskStatus(taskStatusList.get(i));
            criteriaList.add(criteria);
        }
        projectService.updateTask(task, taskID);
        projectService.updateTaskAcceptCriteria(taskID, criteriaList);
        return "redirect:/project/" + projectID + "/tasks";
    }

    @PostMapping("/{projectID}/{taskID}/deleteTask")
    public String deleteTask(@PathVariable int taskID, @PathVariable int projectID) {
        projectService.deleteTask(taskID);
        return "redirect:/project/" + projectID + "/tasks";
    }


    @GetMapping("/{projectID}/{taskID}/taskDetails")
    public String viewTaskDetails(@PathVariable int projectID, @PathVariable int taskID, Model model) {
        Task taskOpen = projectService.findOpenTask(taskID);
        Task taskClosed = projectService.findClosedTask(taskID);
        List<TaskAcceptCriteria> taskAcceptCriteria = projectService.findTaskAcceptCriteria(taskID);

        model.addAttribute("projectID", projectID);
        model.addAttribute("openTaskObject", taskOpen);
        model.addAttribute("closedTaskObject", taskClosed);
        model.addAttribute("taskAcceptCriteria", taskAcceptCriteria);
        return "project_task_details";
    }

    @PostMapping("/updateTaskCriteria")
    public String updateCriteria(@RequestParam("taskID") int taskID,
                                 @RequestParam("projectID") int projectID,
                                 @RequestParam("criteriaID") List<Integer> criteriaIDs,
                                 @RequestParam(value = "criteriaStatus", required = false) List<Integer> criteriaStatus,
                                 RedirectAttributes redirectAttributes) {

        List<TaskAcceptCriteria> criteriaList = new ArrayList<>();

        for (Integer criteriaID : criteriaIDs) {
            boolean taskStatus = criteriaStatus != null && criteriaStatus.contains(criteriaID);
            String criteriaString = projectService.getCriteriaString(criteriaID);
            TaskAcceptCriteria criteria = new TaskAcceptCriteria(criteriaID, taskStatus, criteriaString);
            criteriaList.add(criteria);
        }

        projectService.updateTaskAcceptCriteria(taskID, criteriaList);

        redirectAttributes.addFlashAttribute("successMessage", "Criteria updated successfully!");
        return "redirect:/project/" + projectID + "/" + taskID + "/taskDetails";
    }


    @GetMapping("/{projectID}/{taskID}/assignUsers")
    public String assignUsers(@PathVariable int projectID, @PathVariable int taskID, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Integer loggedInUserId = (Integer) session.getAttribute("userID");
        if (loggedInUserId != null && userService.isProjectManager(loggedInUserId) || loggedInUserId != null && userService.isAdmin(loggedInUserId)) {
            model.addAttribute("projectID", projectID);
            model.addAttribute("taskID", taskID);
            model.addAttribute("users", userService.findAllUsers());
            return "project_assign_users";
        } else {
            redirectAttributes.addFlashAttribute("error", "You do not have permission to assign users.");
            return "redirect:/projectList";
        }
    }

    @PostMapping("/{projectID}/{taskID}/assignUserToTask")
    public String assignUserToTask(@PathVariable int projectID, @PathVariable int taskID, @RequestParam("userID") int userID) {
        projectService.assignUserToTask(userID, taskID);
        return "redirect:/project/" + projectID + "/tasks";
    }

    @GetMapping("/{projectID}/budget")
    public String budgetOverview(@ModelAttribute("projectObject") Project project, @PathVariable int projectID, Model model) {
        projectService.findProjectById(projectID);
        model.addAttribute("budgetSpent", projectService.getBudgetSpent(projectID));
        model.addAttribute("budgetRemaining", projectService.getBudgetRemaining(projectID));
        return "budgetOverview";
    }


    @GetMapping("/{projectID}/time")
    public String timeOverview(@ModelAttribute("projectObject") Project project, @PathVariable("projectID") int projectID, Model model) {
        projectService.findProjectById(projectID);

        double totalEstTime = projectService.getTotalEstimatedTime(projectID);
        double totalActualTime = projectService.getTotalActualTime(projectID);
        Duration totalDuration = projectService.getTotalDuration(projectID);

        Period period = Period.ofDays((int) totalDuration.toDays());
        long hours = totalDuration.toHours() % 24;

        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();


        model.addAttribute("project", project);
        model.addAttribute("totalEstTime", totalEstTime);
        model.addAttribute("totalActualTime", totalActualTime);
        model.addAttribute("years", years);
        model.addAttribute("months", months);
        model.addAttribute("days", days);
        model.addAttribute("hours", hours);

        return "timeOverview";
    }


    @PostMapping("/{projectID}/{taskID}/removeUserFromTask")
    public String removeUserFromTask(@PathVariable int projectID, @PathVariable int taskID, @RequestParam("userID") int userID, HttpSession session, RedirectAttributes redirectAttributes) {
        Integer loggedInUserId = (Integer) session.getAttribute("userID");
        if (loggedInUserId != null && (userService.isProjectManager(loggedInUserId) || userService.isAdmin(loggedInUserId))) {
            projectService.removeAssignedUserToTask(userID, taskID);
            redirectAttributes.addFlashAttribute("message", "User successfully removed from task.");
        } else {
            redirectAttributes.addFlashAttribute("error", "You do not have permission to remove users.");
        }
        return "redirect:/project/" + projectID + "/" + taskID + "/assignedUsers";
    }

    @GetMapping("/{projectID}/{taskID}/assignedUsers")
    public String viewAssignedUsers(@PathVariable int projectID, @PathVariable int taskID, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Integer loggedInUserId = (Integer) session.getAttribute("userID");
        if (loggedInUserId != null && (userService.isProjectManager(loggedInUserId) || userService.isAdmin(loggedInUserId))) {
            List<User> assignedUsers = projectService.getAssignedUsers(taskID);
            model.addAttribute("projectID", projectID);
            model.addAttribute("taskID", taskID);
            model.addAttribute("assignedUsers", assignedUsers);
            return "project_assigned_users";
        } else {
            redirectAttributes.addFlashAttribute("error", "You do not have permission to view assigned users.");
            return "redirect:/projectList";
        }

    }
}

