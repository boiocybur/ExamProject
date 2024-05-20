package com.example.examproject.repository;

import com.example.examproject.controller.ProjectListController;
import com.example.examproject.model.ProjectList;
import com.example.examproject.service.ProjectListService;
import com.example.examproject.service.ProjectService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProjectListControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectListService projectListService;

    @Mock
    private ProjectService projectService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ProjectListController projectListController = new ProjectListController(projectListService, projectService);
        mockMvc = MockMvcBuilders.standaloneSetup(projectListController).build();
    }

    @Test
    public void testShowAllProjectLists() throws Exception {
        when(projectListService.showAllProjectLists()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/projectList/showAllProjectLists"))
                .andExpect(status().isOk())
                .andExpect(view().name("placeholder_show_allProjectLists"))
                .andExpect(model().attributeExists("allProjectLists"));
    }

    @Test
    public void testUpdateProject() throws Exception {
        mockMvc.perform(post("/projectList/updateProjectList")
                        .param("projectListID", "1")
                        .param("projectListName", "UpdatedProjectList"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projectList_show_projectList"));
        verify(projectListService, times(1)).updateProjectList(any(ProjectList.class));
    }
}
