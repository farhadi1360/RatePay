package com.ratepay.bugtracker.api;

import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.exceptions.custom.IllegalActionException;
import com.ratepay.bugtracker.services.ProjectService;
import com.ratepay.bugtracker.services.TicketService;
import com.ratepay.bugtracker.services.UserService;
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.mapper.ProjectMapper;
import com.ratepay.client.bugtracker.mapper.TicketMapper;
import com.ratepay.client.bugtracker.mapper.UserMapper;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.client.bugtracker.models.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;
    private final ProjectService projectService;
    private final TicketMapper ticketMapper;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;


    //add ticket (user)
    @PostMapping
    @Secured("ROLE_USER")
    public ResponseEntity<?> addTicket(@RequestParam(name = "code") String projectCode,
                                       @RequestBody @Valid TicketModel ticketRequest,
                                       Principal principal) {
        UserModel userModel = userService.findByUsername(principal.getName());
        ProjectModel projectModel = projectService.findByCode(projectCode);
        TicketModel ticketModel = ticketService.getTicketFromRequest(ticketRequest, userModel, projectModel);
        Project project = projectMapper.toEntity(projectModel);
        project.addTicket(ticketMapper.toEntity(ticketModel));
        ticketService.save(ticketModel);
        projectService.save(projectMapper.toModel(project));
        return ResponseEntity.ok(ticketModel);
    }

    //add ticket (developer, manager)
    @PostMapping("/{projectId}")
    @Secured("ROLE_DEVELOPER")
    public ResponseEntity<?> addTicketAsDeveloper(@PathVariable Long projectId,
                                                  @RequestBody @Valid TicketModel ticketRequest,
                                                  Principal principal) {
        UserModel userModel = userService.findByUsername(principal.getName());
        ProjectModel projectModel = projectService.findById(projectId);
        TicketModel ticketModel = ticketService.getTicketFromRequest(ticketRequest, userModel, projectModel);
        Project project = projectMapper.toEntity(projectModel);
        project.addTicket(ticketMapper.toEntity(ticketModel));
        ticketService.save(ticketModel);
        projectService.save(projectMapper.toModel(project));
        return ResponseEntity.ok(ticketModel);
    }

    //edit ticket

    //delete ticket


    //assign ticket to developer
    @PutMapping("/{ticketId}/assign")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> assignTicketToDeveloper(@PathVariable Long ticketId,
                                                     @RequestParam(name = "developerId") Long developerId,
                                                     Principal principal) {
        UserModel userModel = userService.findByUsername(principal.getName());
        UserModel developerModel = userService.findById(developerId);
        TicketModel ticketModel = ticketService.findById(ticketId);
        ProjectModel projectModel = ticketModel.getProject();

        if (!projectModel.getProjectManager().equals(userModel)) {
            throw new EntityNotFoundException("Project with id " + ticketModel.getProject().getId() + " doesn't exist");
        }

        if (!developerModel.getProjectsWorkingOn().contains(projectModel)) {
            throw new IllegalActionException("This developer isn't assigned to this project");
        }
        ticketModel.setDeveloper(developerModel);

        User developer = userMapper.toEntity(developerModel);
        developer.addTicketsWorkingOn(ticketMapper.toEntity(ticketModel));

        ticketService.save(ticketModel);
        userService.save(userMapper.toModel(developer));

        return ResponseEntity.ok().build();
    }


    //remove developer from ticket
    @PutMapping("/{ticketId}/remove")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> removeDeveloperFromTicket(@PathVariable Long ticketId,
                                                       @RequestParam(name = "developerId") Long developerId,
                                                       Principal principal) {
        UserModel userModel = userService.findByUsername(principal.getName());
        UserModel developerModel = userService.findById(developerId);
        TicketModel ticketModel = ticketService.findById(ticketId);
        ProjectModel projectModel = ticketModel.getProject();

        if (!projectModel.getProjectManager().equals(userModel)) {
            throw new EntityNotFoundException("Project with id " + ticketModel.getProject().getId() + " doesn't exist");
        }

        if (!ticketModel.getDeveloper().equals(developerModel)) {
            throw new IllegalActionException("This developer is not assigned to this ticket!");
        }
        User developer = userMapper.toEntity(developerModel);
        developer.removeTicketsWorkingOn(ticketMapper.toEntity(ticketModel));
        ticketModel.setDeveloper(null);
        userService.save(userMapper.toModel(developer));
        ticketService.save(ticketModel);

        return ResponseEntity.ok().build();
    }
}
