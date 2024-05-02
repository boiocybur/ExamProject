package com.example.examproject.controller;

import com.example.examproject.model.User;
import com.example.examproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class UserController {
    UserService userService;
    private User user;

    public UserController(UserService userService) {
        this.userService = userService;
        this.user = new User();
    }

    @GetMapping("")
    public String userPage() {
        return "UserPage";
    }
}
