package com.example.examproject.service;

import org.springframework.stereotype.Service;
import com.example.examproject.repository.OverviewRepository;

@Service
public class OverviewService {


    private OverviewRepository overviewRepository;

    public OverviewService(OverviewRepository overviewRepository) {
        this.overviewRepository = overviewRepository;
    }

    public boolean showProjects() {
        return overviewRepository.showProjects();
    }

    public boolean createProject() {
        return overviewRepository.createProject();
    }

    public boolean deleteProject() {
        return overviewRepository.deleteProject();
    }

    public boolean updateProject() {
        return overviewRepository.updateProject();
    }
}
