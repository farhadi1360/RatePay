package com.ratepay.bugtracker.api;

import com.ratepay.bugtracker.services.TicketService;
import com.ratepay.bugtracker.services.UserService;
import com.ratepay.client.bugtracker.mapper.TicketMapper;
import com.ratepay.client.bugtracker.mapper.UserMapper;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.client.bugtracker.models.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TicketService ticketService;
    private final UserMapper userMapper;
    private final TicketMapper ticketMapper;

    @GetMapping("/user/{userId}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        UserModel user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/managed-projects")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getProjectsManagedMy(Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(user.getProjects());
    }

    @DeleteMapping("/user")
    @Secured("ROLE_USER")
    public ResponseEntity<?> deleteAccount(Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        for (ProjectModel p : user.getProjectsWorkingOn()) {
            p.getDevelopers().remove(user);
        }
        for (TicketModel t : user.getTicketsWorkingOn()) {
            t.setDeveloper(null);
            ticketService.save(t);
        }
        userService.delete(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/projects")
    @Secured("ROLE_DEVELOPER")
    public ResponseEntity<?> getAllProjectsForDeveloper(Principal principal) {
        UserModel developer = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(developer.getProjectsWorkingOn());
    }

    @GetMapping("/user/{developerId}/projects")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getAllProjectsForDeveloper(@PathVariable Long developerId) {
        UserModel developer = userService.findById(developerId);
        return ResponseEntity.ok(developer.getProjectsWorkingOn());
    }

    @GetMapping("/user/{developerId}/tickets")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getAllTicketsForDeveloper(@PathVariable Long developerId) {
        UserModel developer = userService.findById(developerId);
        return ResponseEntity.ok(developer.getTicketsWorkingOn());
    }
}
