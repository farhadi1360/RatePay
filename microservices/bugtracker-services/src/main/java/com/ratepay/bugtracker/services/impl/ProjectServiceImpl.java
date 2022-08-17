package com.ratepay.bugtracker.services.impl;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */

import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.exceptions.custom.IllegalActionException;
import com.ratepay.bugtracker.repository.ProjectRepository;
import com.ratepay.bugtracker.repository.TicketRepository;
import com.ratepay.bugtracker.repository.UserRepository;
import com.ratepay.bugtracker.services.ProjectService;
import com.ratepay.bugtracker.services.RoleService;
import com.ratepay.bugtracker.services.UserService;
import com.ratepay.bugtracker.utils.ProjectUtils;
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.enume.RoleName;
import com.ratepay.client.bugtracker.mapper.ProjectMapper;
import com.ratepay.client.bugtracker.mapper.RoleMapper;
import com.ratepay.client.bugtracker.mapper.TicketMapper;
import com.ratepay.client.bugtracker.mapper.UserMapper;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.core.dto.ResponseDto;
import com.ratepay.core.service.impl.MainServiceSQLModeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ProjectServiceImpl extends MainServiceSQLModeImpl<ProjectModel, Project, Long> implements ProjectService<ProjectModel, Long> {
    private static final ResponseDto DONE = new ResponseDto(true);
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, RoleService roleService, RoleMapper roleMapper, UserRepository userRepository, TicketMapper ticketMapper,TicketRepository ticketRepository) {
        super(projectMapper, projectRepository);
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.roleMapper = roleMapper;
        this.userRepository = userRepository;
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
    }


    public ResponseDto createProject(ProjectModel projectModel, Principal principal) throws EntityNotFoundException {

        Optional<User> projectManager = userService.findUserByPrincipal(principal);
        if (projectManager.isPresent()) {
            Project project = new Project(projectModel.getName(), projectManager.get());
            projectModel.getDevelopers().stream().forEach(developer -> {
                Optional<User> userAsDeveloper = userService.findUserByUserIdAsDeveloper(developer.getId());
                userAsDeveloper.ifPresent(dev -> {
                    project.addDeveloper(dev);
                });
            });
            projectRepository.save(project);
            log.info("Project {}, by {}  was successfully saved", project.getName(), projectManager.get().getUsername());
            projectManager.get().addProjects(project);
            projectManager.get().addRole(roleMapper.toEntity(roleService.findByRoleName(RoleName.ROLE_MANAGER)));
            projectManager.get().addRole(roleMapper.toEntity(roleService.findByRoleName(RoleName.ROLE_DEVELOPER)));
            projectManager.get().addProjects(project);
            userRepository.save(projectManager.get());
            log.info("ProjectManager  {}   was successfully saved information about project", projectManager.get().getUsername(), projectManager.get().getUsername());
            return DONE;
        } else {
            throw new EntityNotFoundException("ProjectManager base on principal of request  not found!");
        }
    }

    public ResponseDto editProjectManager(Long projectId,ProjectModel projectModel) throws EntityNotFoundException{
        Optional<Project> project = projectRepository.findById(projectId);
        Optional<User> projectManager = userRepository.findById(projectModel.getProjectManager().getId());
        List<User> newDevelopers = ProjectUtils.getAllDeveloper(projectModel.getDevelopers(),userRepository);
        List<Ticket> newTicket = ProjectUtils.getAllTicket(projectModel.getTickets(), ticketRepository);
        project.ifPresentOrElse(
                (prj)->{
                    prj.setCode(projectModel.getCode());
                    prj.setName(projectModel.getName());
                    projectManager.ifPresentOrElse((newProjectManager)->{
                        prj.setProjectManager(newProjectManager);
                        if(newDevelopers.size()>0)  prj.setDevelopers(newDevelopers);
                        if(newTicket.size()>0) prj.setTickets(newTicket);
                        projectRepository.save(prj);
                            },
                            ()->{throw new EntityNotFoundException("ProjectManager with username " + projectModel.getProjectManager().getUserName() + " not found!");
                    });
                },
                ()->{  throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");}
        );
        return DONE;
    }

    public ResponseDto assignDeveloperToProject(Long projectId, Long developerId, Principal principal) throws EntityNotFoundException, IllegalActionException {
        Optional<User> projectManager = userService.findUserByPrincipal(principal);
        if (projectManager.isPresent()) {
            Optional<Project> project = projectRepository.findById(projectId);
            project.ifPresentOrElse(
                    (prj) -> {
                        if (!prj.getProjectManager().equals(projectManager.get())) {
                            throw new EntityNotFoundException("projectManager is not equal to  current user");
                        }
                        Optional<User> developer = userService.findUserByUserIdAsDeveloper(developerId);
                        developer.ifPresent(dev -> {
                            dev.addProjectsWorkingOn(prj);
                            dev.addRole(roleMapper.toEntity(roleService.findByRoleName(RoleName.ROLE_DEVELOPER)));
                            try {
                                projectRepository.save(prj);
                                userRepository.save(dev);
                                log.info("{}, Project was successfully assigned to {}", prj.getName(), dev.getUsername());
                            } catch (Exception e) {
                                log.error(e.getMessage());
                                throw new IllegalActionException("transaction does not doing");
                            }

                        });
                    },
                    () -> {
                        throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
                    });
        } else {
            throw new EntityNotFoundException("ProjectManager base on principal of request is not found!");
        }
        return DONE;
    }

    public ResponseDto removeDeveloperFromProject(Long projectId, Long developerId, Principal principal) throws EntityNotFoundException {
        Optional<User> currentUser = userService.findUserByPrincipal(principal);
        if (currentUser.isPresent()) {
            Optional<Project> project = projectRepository.findById(projectId);
            project.ifPresentOrElse((prj) -> {
                if (!prj.getProjectManager().equals(currentUser.get())) {
                    throw new EntityNotFoundException("projectManager is not equal to  current user");
                }
                Optional<User> developer = userService.findUserByUserIdAsDeveloper(developerId);
                if (developer.isPresent()) {
                    if ((developer.get().getId().equals(currentUser.get().getId()))) {
                        throw new IllegalActionException("You can't remove yourself from your project!");
                    }
                    developer.get().removeProjectWorkingOn(prj);
                    try {
                        projectRepository.save(prj);
                        log.info("{}, as Developer  was successfully removed from  {} project", developer.get().getUsername(), prj.getName());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new IllegalActionException("transaction does not doing");
                    }
                } else {
                    throw new EntityNotFoundException("Developer with id " + developerId + " not found!");
                }
            }, () -> {
                throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
            });

        } else {
            throw new EntityNotFoundException("currentUser base on principal of request is not found!");
        }
        return DONE;
    }

    public List<TicketModel> getAllTicketsByProjectId(Long projectId, Principal principal) throws EntityNotFoundException {
        Optional<User> currentUser = userService.findUserByPrincipal(principal);
        if (currentUser.isPresent()) {
            Optional<Project> project = projectRepository.findById(projectId);
            if (project.isPresent()) {
                if (!project.get().getProjectManager().equals(currentUser)) {
                    throw new EntityNotFoundException("projectManager is not equal to  current user");
                }
                return ticketMapper.toModel(project.get().getTickets());
            } else {
                throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
            }
        } else {
            throw new EntityNotFoundException("currentUser base on principal of request is not found!");
        }
    }

    public Optional<ProjectModel> findByCode(String code) throws EntityNotFoundException {
        return Optional.of(projectMapper.toModel(projectRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException("Project with code " + code + " doesn't exist!")
        )));
    }
}
