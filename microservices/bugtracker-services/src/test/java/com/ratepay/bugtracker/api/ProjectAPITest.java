package com.ratepay.bugtracker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratepay.bugtracker.services.ProjectService;
import com.ratepay.bugtracker.utils.ProjectPrepareInfo;
import com.ratepay.client.bugtracker.mapper.UserMapper;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.client.bugtracker.models.UserModel;
import com.ratepay.core.dto.ResponseDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectAPITest {
    private static final ResponseDto DONE = new ResponseDto(true);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    UserMapper userMapper;
    @MockBean
    ProjectService projectService;

    @DisplayName("Test API Mock Create Project ")
    @WithMockUser(roles = "USER")
    @Test
    @Order(1)
    @Commit
    @SneakyThrows
    void createProjectTest() {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        ProjectModel projectModel = ProjectPrepareInfo.getSimpleProjectModelForAPTTest();
        when(projectService.createProject(projectModel, mockPrincipal)).thenReturn(DONE);

        String json = objectMapper.writeValueAsString(projectModel);
        mockMvc.perform(post("/api/v1/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
        ;
    }

    @DisplayName("Test API Mock EDIT Project ")
    @WithMockUser(roles = "USER")
    @Test
    @Order(2)
    @Commit
    @SneakyThrows
    void editProjectTest() {
        ProjectModel projectModel = ProjectPrepareInfo.getSimpleProjectModelForAPTTest();
        UserModel projectManager = new UserModel();
        projectManager.setId(1l);
        projectModel.setName("IssueTracker");
        projectModel.setProjectManager(projectManager);
        Long projectId = 1l;
        when(projectService.editProjectManager(projectId, projectModel)).thenReturn(DONE);

        String json = objectMapper.writeValueAsString(projectModel);
        mockMvc.perform(post("/api/v1/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
        ;
    }

    @DisplayName("Test API Mock Assign Developer To Project ")
    @WithMockUser(roles = "MANAGER")
    @Test
    @Order(3)
    @Commit
    @SneakyThrows
    void assignDeveloperToProjectTest() {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Long projectId = 1l;
        Long developerId = 1l;
        when(projectService.assignDeveloperToProject(projectId, developerId, mockPrincipal)).thenReturn(DONE);
        mockMvc.perform(put("/api/v1/project/{projectId}/assign-developer", projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("developerId", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("Test API Mock Remove Developer From Project ")
    @WithMockUser(roles = "MANAGER")
    @Test
    @Order(4)
    @Commit
    @SneakyThrows
    void removeDeveloperFromProject() {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Long projectId = 1l;
        Long developerId = 1l;
        when(projectService.removeDeveloperFromProject(projectId, developerId, mockPrincipal)).thenReturn(DONE);
        mockMvc.perform(delete("/api/v1/project/{projectId}/remove-developer", projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"));
    }

}
