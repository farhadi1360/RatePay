package com.ratepay.bugtracker.api;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */

import com.ratepay.bugtracker.services.UserService;
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.client.bugtracker.models.UserModel;
import com.ratepay.core.dto.ResponseDto;
import com.ratepay.core.rest.impl.BaseRestSqlModeImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
public class UserAPI extends BaseRestSqlModeImpl<UserModel, Long> {
    private final UserService userService;

    public UserAPI(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @GetMapping("/managed-projects")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getProjectsManagedMy(Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(user.getProjects());
    }

    @DeleteMapping("/remove-principal")
    @Secured("ROLE_USER")
    public ResponseEntity<?> deleteAccount(Principal principal) {
        ResponseDto responseDto = userService.deleteUser(principal);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/projects")
    @Secured("ROLE_DEVELOPER")
    public ResponseEntity<?> getAllProjectsForDeveloper(Principal principal) {
        Set<Project> projectListByDeveloper = userService.getAllProjectsByDeveloper(principal);
        return ResponseEntity.ok(projectListByDeveloper);
    }

    @GetMapping("/{developerId}/projects")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getAllProjectsForDeveloper(@PathVariable Long developerId) {
        Set<Project> projectListByDeveloper = userService.getAllProjectsByDeveloperId(developerId);
        return ResponseEntity.ok(projectListByDeveloper);
    }
    @GetMapping("/user/{developerId}/tickets")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getAllTicketsForDeveloper(@PathVariable Long developerId) {
        Set<Ticket> ticketListByDeveloper = userService.getAllTicketsForDeveloper(developerId);
        return ResponseEntity.ok(ticketListByDeveloper);
    }

}
