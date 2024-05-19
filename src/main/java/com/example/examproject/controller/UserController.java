package com.example.examproject.controller;

import com.example.examproject.model.User;
import com.example.examproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("")
public class UserController {
    UserService userService;
    private User user;

    public UserController(UserService userService) {
        this.userService = userService;
        this.user = new User();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userEmail") String userEmail,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        if (userService.loginUser(userEmail, password)) {
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("userEmail", userEmail);
            session.setAttribute("userID", userService.findUserById(userEmail));
            return "redirect:/projectList";
        } else {
            redirectAttributes.addAttribute("error", true);
            return "redirect:";
        }
    }
    @GetMapping("/result")
    public String result(HttpSession session, Model model) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        if (Boolean.TRUE.equals(isLoggedIn)) {
            String userEmail = (String) session.getAttribute("userEmail");
            model.addAttribute("userEmail", userEmail);
            return "/login";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
    @GetMapping("/createUser")
    public String register(Model model){
        model.addAttribute("userForm", new User());
        return "createUser";
    }
    @PostMapping("/createUser")
    public String registerUser(@ModelAttribute("userForm") User userForm, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        Integer loggedInUserId = (Integer) session.getAttribute("userID");
        if (loggedInUserId != null && userService.isAdmin(loggedInUserId)) {
            boolean isRegistered = userService.createUser(userForm);
            if (isRegistered) {
                redirectAttributes.addFlashAttribute("success", "User created successfully!");
                return "redirect:/login";
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to create user.");
                return "redirect:/createUser";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "You do not have permission to perform this action.");
            return "redirect:/createUser";
        }
    }
    @PostMapping("/{userEmail}/delete")
    public String deleteUser(@PathVariable String userEmail, HttpSession session, RedirectAttributes redirectAttributes) {
        Integer loggenInUserId = (Integer) session.getAttribute("userID");
        if (loggenInUserId != null && userService.isAdmin(loggenInUserId)) {
            userService.deleteUser(userEmail);
        }
        redirectAttributes.addFlashAttribute("error", "You do not have permission to delete users.");
        return "redirect:/showUsers";
    }

    @GetMapping("/{userID}/edit")
    public String editUser(@PathVariable int userID, HttpSession session, Model model) {
        Integer loggenInUserId = (Integer) session.getAttribute("userID");
        User user = userService.getUserById(userID);
        if (user != null && loggenInUserId != null && userService.isAdmin(loggenInUserId)) {
            model.addAttribute("user", user);
            return "editUser";
        } else {
            return "redirect:/login";
        }
    }
    @PostMapping("/save")
    public String saveEdit(@ModelAttribute User savedEdit) {
        userService.editUser(savedEdit);
        return "redirect:/login";
    }
   @GetMapping("/showUsers")
   public String showAllProjectLists(Model model) {
       model.addAttribute("allUsers", userService.findAllUsers());
       return "listUsers";
   }
}
