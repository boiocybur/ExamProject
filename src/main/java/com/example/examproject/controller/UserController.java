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
@RequestMapping("/user")
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
            session.setAttribute("userid", userService.findUserById(userEmail));
            return "redirect:/user/login";
        } else {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/user/login";
        }
    }
    @GetMapping("/result")
    public String result(HttpSession session, Model model) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        if (Boolean.TRUE.equals(isLoggedIn)) {
            String userEmail = (String) session.getAttribute("userEmail");
            model.addAttribute("userEmail", userEmail);
            return "/user/login";
        } else {
            return "redirect:/user/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/user/login";
    }
    @GetMapping("/createUser")
    public String register(Model model){
        model.addAttribute("userForm", new User());
        return "createUser";
    }
    @PostMapping("/createUser")
    public String registerUser(@ModelAttribute("userForm") User userForm, Model model, RedirectAttributes redirectAttributes) {
        boolean isRegistered = userService.createUser(userForm);

        if (isRegistered) {
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return "redirect:/user/login";
        } else {
            model.addAttribute("error", "Registration failed. Try again.");
            return "createUser";
        }
    }

    @GetMapping("/{userEmail}/delete")
    public String deleteUser(@PathVariable String userEmail, HttpSession session) {
        String loggedInUserEmail = (String) session.getAttribute("userEmail");
        if (loggedInUserEmail != null && loggedInUserEmail.equals(userEmail)) {
            userService.deleteUser(userEmail);
        }
        return "redirect:/user/login";
    }

   /* @GetMapping("/{userId}/edit")
    public String editUser(@PathVariable int userId, HttpSession session, Model model) {
        String loggedInUserEmail = (String) session.getAttribute("userEmail");
        User user = userService.getUserById(userId);
        if (user != null && loggedInUserEmail != null && user.getUserEmail().equals(loggedInUserEmail)) {
            model.addAttribute("user", user);
            return "editUser";
        } else {
            return "redirect:/user/login";
        }
    }

    /*@PostMapping("/save")
    public String saveEdit(@ModelAttribute User savedEdit, HttpSession session) {
        String loggedInUsername = (String) session.getAttribute("username");
        if (loggedInUsername != null && savedEdit.getUserName().equals(loggedInUsername)) {
            userService.editUser(savedEdit);
        }
        return "redirect:/user/login";
    }*/
    @PostMapping("/save")
    public String saveEdit(@ModelAttribute User savedEdit) {
        userService.editUser(savedEdit);
        return "redirect:/user/login";
    }

}
