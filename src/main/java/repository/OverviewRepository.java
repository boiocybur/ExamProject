package repository;

import model.Overview;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class OverviewRepository {
    @Value("${spring.datasource.url}")
    String db_url;
    @Value("${spring.datasource.username}")
    String uid;
    @Value("${spring.datasource.password}")
    String pwd;

    private final Overview overview;

    public OverviewRepository(Overview overview) {
        this.overview = overview;
    }


    public boolean showProjects() {
        return true;
    }

    public boolean createProject() {
        return true;
    }

    public boolean deleteProject() {
        return true;
    }

    public boolean updateProject() {
        return true;
    }
}
