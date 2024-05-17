package com.example.examproject.repository;

import com.example.examproject.controller.ProjectListController;
import com.example.examproject.model.ProjectList;
import com.example.examproject.service.ProjectListService;
import com.example.examproject.service.ProjectService;
import com.example.examproject.service.UserService;
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

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ProjectListController projectListController = new ProjectListController(projectListService, projectService, userService);
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
    public void testCreateProjectListForm() throws Exception {
        mockMvc.perform(get("/projectList/createProjectList"))
                .andExpect(status().isOk())
                .andExpect(view().name("projectList_create_projectList"))
                .andExpect(model().attributeExists("projectListObject"));
    }

    @Test
    public void testCreateProjectList() throws Exception {
        mockMvc.perform(post("/projectList/createProjectList")
                        .param("projectListName", "TestProjectList"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projectList_show_projectList"));
        verify(projectListService, times(1)).createProjectList(any(ProjectList.class));
    }
}
