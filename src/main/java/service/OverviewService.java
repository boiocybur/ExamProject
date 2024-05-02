package service;

import org.springframework.stereotype.Service;
import repository.OverviewRepository;

@Service
public class OverviewService {

    private final OverviewRepository overviewRepository;

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
