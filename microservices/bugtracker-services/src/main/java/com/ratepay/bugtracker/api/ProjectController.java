package com.ratepay.bugtracker.api;

import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.exceptions.custom.IllegalActionException;
import com.ratepay.bugtracker.services.ProjectService;
import com.ratepay.bugtracker.services.RoleService;
import com.ratepay.bugtracker.services.UserService;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.enume.RoleName;
import com.ratepay.client.bugtracker.mapper.ProjectMapper;
import com.ratepay.client.bugtracker.mapper.UserMapper;
import com.ratepay.client.bugtracker.models.ApiResponse;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.client.bugtracker.models.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {
    private final UserService userService;
    private final ProjectService projectService;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;

    @PostMapping
    @Secured("ROLE_USER")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectModel projectRequest,
                                           Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        ProjectModel project = projectService.projectFromRequest(projectRequest, user);
        projectMapper.toEntity(project).addDeveloper(userMapper.toEntity(user));
        projectService.save(project);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{projectId}")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId,
                                           Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        ProjectModel project = projectService.findById(projectId);
        if (!project.getProjectManager().equals(user)) {
            //throw this error so that the user doesn't know
            // if this id even exists within other projects
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        if (user.getProjects().size() == 1) {
            System.out.println("Here");
            userMapper.toEntity(user).removeRole(roleService.findByRoleName(RoleName.ROLE_MANAGER));
            userService.save(user);
        }

        projectService.delete(project);
        return ResponseEntity.ok(new ApiResponse("Project " + project.getName() + " successfully deleted!"));
    }

    @PutMapping("/{projectId}")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> editProject(@PathVariable Long projectId,
                                         @RequestBody ProjectModel projectRequest,
                                         Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        ProjectModel project = projectService.findById(projectId);

        if (!project.getProjectManager().equals(user)) {
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        project.setName(projectRequest.getName());
        projectService.save(project);

        return ResponseEntity.ok(project);
    }

    @GetMapping("/{projectId}")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getProjectById(@PathVariable Long projectId,
                                            Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        ProjectModel project = projectService.findById(projectId);

        if (!project.getProjectManager().equals(user)) {
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        return ResponseEntity.ok(project);
    }

    @PutMapping("/{projectId}/developer")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> assignDeveloperToProject(@PathVariable Long projectId,
                                                      @RequestParam(name = "id") Long developerId,
                                                      Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        ProjectModel project = projectService.findById(projectId);

        if (!project.getProjectManager().equals(user)) {
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        UserModel developerModel = userService.findById(developerId);
        User developer = userMapper.toEntity(developerModel);
        developer.addProjectsWorkingOn(projectMapper.toEntity(project));
        developer.addRole(roleService.findByRoleName(RoleName.ROLE_DEVELOPER));
        projectService.save(project);
        userService.save(userMapper.toModel(developer));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}/developer")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> removeDeveloperFromProject(@PathVariable Long projectId,
                                                        @RequestParam(name = "id") Long developerId,
                                                        Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        ProjectModel project = projectService.findById(projectId);

        if (!project.getProjectManager().equals(user)) {
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        UserModel developerModel = userService.findById(developerId);

        if (developerModel.getId().equals(user.getId())) {
            throw new IllegalActionException("You can't remove yourself from your project!");
        }

        User developer = userMapper.toEntity(developerModel);
        developer.removeProjectWorkingOn(projectMapper.toEntity(project));

        if (developer.getProjectsWorkingOn().isEmpty()) {
            developer.removeRole(roleService.findByRoleName(RoleName.ROLE_DEVELOPER));
        }

        projectService.save(project);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{projectId}/developers")
    @Secured({"ROLE_MANAGER", "ROLE_DEVELOPER"})
    public ResponseEntity<?> getAllDevelopersForProject(@PathVariable Long projectId,
                                                        Principal principal) {

        UserModel user = userService.findByUsername(principal.getName());
        ProjectModel project = projectService.findById(projectId);

        if (!project.getProjectManager().equals(user)) {
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        return ResponseEntity.ok(project.getDevelopers());
    }

    @GetMapping("/{projectId}/tickets")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getAllTickets(@PathVariable Long projectId, Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        ProjectModel project = projectService.findById(projectId);

        if (!project.getProjectManager().equals(user)) {
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        return ResponseEntity.ok(project.getTickets());
    }
}
