package com.example.examproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.examproject.service.OverviewService;

@Controller
@RequestMapping("/overview")
public class OverviewController {

    private OverviewService overviewService;

    public OverviewController(OverviewService overviewService) {
        this.overviewService = overviewService;
    }

    @GetMapping("/show")
    public String showProjects() {
        return null;
    }

    @GetMapping("/create")
    public String createProject(Model model) {
        return null;
    }

    @PostMapping("/create")
    public String createProject() {
        return null;
    }

    @GetMapping("/{id]/delete")
    public String deleteProject() {
        return null;
    }

    @GetMapping("/{id}/update")
    public String updateProject(Model model) {
        return null;
    }

    @PostMapping("/update")
    public String updateProject() {
        return null;
    }




}
