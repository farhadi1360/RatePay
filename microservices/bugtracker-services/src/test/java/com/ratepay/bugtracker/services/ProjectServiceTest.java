package com.ratepay.bugtracker.services;

import com.ratepay.bugtracker.repository.ProjectRepository;
import com.ratepay.bugtracker.repository.UserRepository;
import com.ratepay.bugtracker.services.impl.ProjectServiceImpl;
import com.ratepay.bugtracker.utils.ProjectPrepareInfo;
import com.ratepay.bugtracker.utils.UserPrepareInfo;
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.enume.RoleName;
import com.ratepay.client.bugtracker.mapper.ProjectMapper;
import com.ratepay.client.bugtracker.mapper.RoleMapper;
import com.ratepay.client.bugtracker.mapper.TicketMapper;
import com.ratepay.client.bugtracker.mapper.UserMapper;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.client.bugtracker.models.RoleModel;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectServiceTest {
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    UserMapper userMapper;
    @Mock
    TicketMapper ticketMapper;
    @Mock
    RoleService roleService;
    @Mock
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleMapper roleMapper;
    @Mock
    ProjectRepository projectRepository;
    @InjectMocks
    ProjectServiceImpl projectService;


    @BeforeEach
    void setMockOutput() {

    }

    @DisplayName("Test Mock Create Project ")
    @Test
    @Order(1)
    public void createProjectTest() {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Project mockProject = Mockito.mock(Project.class);
        Long developerId = 1l;
        when(userService.findUserByPrincipal(mockPrincipal)).thenReturn(Optional.of(UserPrepareInfo.getUser()));
        when(userService.findUserByUserIdAsDeveloper(developerId)).thenReturn(Optional.of(UserPrepareInfo.getUser()));
        when(projectRepository.save(mockProject)).thenReturn(null);
        when(userRepository.save(UserPrepareInfo.getUser())).thenReturn(null);
        ProjectModel projectModel = ProjectPrepareInfo.prepareProjectModelInfo(userMapper);
        assertEquals(UserPrepareInfo.mockAnswer(), projectService.createProject(projectModel, mockPrincipal));
    }

    @DisplayName("Test Mock Edit Project ")
    @Test
    @Order(2)
    public void editProjectManagerTest() {
        Long projectId = 1l;
        Long userId = 1l;
        when(projectRepository.findById(1l)).thenReturn(Optional.of(mockProjectMapper()));
        when(userRepository.findById(userId)).thenReturn(Optional.of(UserPrepareInfo.getUser()));
        ProjectModel projectModel = ProjectPrepareInfo.prepareProjectModelInfo(userMapper);
        assertEquals(UserPrepareInfo.mockAnswer(), projectService.editProjectManager(projectId, projectModel));
    }

    @DisplayName("Test Mock Assign Developer To Project ")
    @Test
    @Order(3)
    public void assignDeveloperToProjectTest() {
        Long projectId = 1l;
        Long developerId = 1l;
        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(projectRepository.findById(1l)).thenReturn(Optional.of(mockProjectMapper()));
        when(userService.findUserByPrincipal(mockPrincipal)).thenReturn(Optional.of(UserPrepareInfo.getUser()));
        when(userService.findUserByUserIdAsDeveloper(developerId)).thenReturn(Optional.of(UserPrepareInfo.getUser()));
        when(roleService.findByRoleName(RoleName.ROLE_DEVELOPER)).thenReturn(mockDeveloperRoleMapper());
        assertEquals(UserPrepareInfo.mockAnswer(), projectService.assignDeveloperToProject(projectId, developerId, mockPrincipal));
    }

    @DisplayName("Test Mock Remove Developer From Project ")
    @Test
    @Order(4)
    public void removeDeveloperFromProjectTest() {
        Long projectId = 1l;
        Long developerId = 1l;
        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(projectRepository.findById(1l)).thenReturn(Optional.of(mockProjectMapper()));
        when(userService.findUserByPrincipal(mockPrincipal)).thenReturn(Optional.of(UserPrepareInfo.getUser()));
        when(userRepository.findById(developerId)).thenReturn(Optional.of(UserPrepareInfo.getUser()));
        when(userService.findUserByUserIdAsDeveloper(developerId)).thenReturn(Optional.of(UserPrepareInfo.getDeveloper()));
        assertEquals(UserPrepareInfo.mockAnswer(), projectService.removeDeveloperFromProject(projectId, developerId, mockPrincipal));
    }




    public Project mockProjectMapper() {
        ProjectModel projectModel = ProjectPrepareInfo.prepareProjectModelInfo(userMapper);
        Project project = projectMapper.toEntity(projectModel);
        project.getProjectManager().setUsername(projectModel.getProjectManager().getUserName());
        return project;
    }

    public RoleModel mockDeveloperRoleMapper() {
        RoleModel roleModel = new RoleModel();
        roleModel.setId(3l);
        roleModel.setRole(RoleName.ROLE_DEVELOPER.name());
        return roleModel;
    }
}
