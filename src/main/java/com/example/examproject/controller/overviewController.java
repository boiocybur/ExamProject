package com.example.examproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class overviewController {
    @GetMapping("")
    public String test(Model model) {
        return "test2";
    }

}
