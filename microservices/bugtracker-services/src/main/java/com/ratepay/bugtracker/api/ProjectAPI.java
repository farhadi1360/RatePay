package com.ratepay.bugtracker.api;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */

import com.ratepay.bugtracker.services.ProjectService;
import com.ratepay.bugtracker.services.UserService;
import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.client.bugtracker.models.UserModel;
import com.ratepay.core.dto.BaseResponseDTO;
import com.ratepay.core.dto.ResponseDto;
import com.ratepay.core.rest.impl.BaseRestSqlModeImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/v1/project")
public class ProjectAPI extends BaseRestSqlModeImpl<ProjectModel, Long> {
    private final ProjectService projectService;
    private final UserService userService;

    public ProjectAPI(ProjectService projectService, UserService userService) {
        super(projectService);
        this.projectService = projectService;
        this.userService = userService;
    }

    @PostMapping("/create")
    @Secured("ROLE_USER")
    public BaseResponseDTO<?> createProject(@Valid @RequestBody ProjectModel projectRequest,
                                            Principal principal) {
        ResponseDto result = projectService.createProject(projectRequest, principal);
        return BaseResponseDTO.ok(result);
    }

    @PutMapping("/{projectId}")
    @Secured("ROLE_MANAGER")
    public BaseResponseDTO<?> editProject(@PathVariable Long projectId,@Valid @RequestBody ProjectModel projectRequest) {
        ResponseDto result = projectService.editProjectManager(projectId,projectRequest);
        return BaseResponseDTO.ok(result);
    }

    @PutMapping("/{projectId}/assign-developer")
    @Secured("ROLE_MANAGER")
    public BaseResponseDTO<?> assignDeveloperToProject(@PathVariable Long projectId,
                                                       @RequestParam(name = "developerId") Long developerId,
                                                       Principal principal) {
        ResponseDto result = projectService.assignDeveloperToProject(projectId, developerId, principal);
        return BaseResponseDTO.ok(result);
    }

    @DeleteMapping("/{projectId}/remove-developer")
    @Secured("ROLE_MANAGER")
    public BaseResponseDTO<?> removeDeveloperFromProject(@PathVariable Long projectId,
                                                         @RequestParam(name = "id") Long developerId,
                                                         Principal principal) {
        ResponseDto result = projectService.removeDeveloperFromProject(projectId, developerId, principal);
        return BaseResponseDTO.ok(result);
    }

    @GetMapping("/{projectId}/tickets")
    @Secured("ROLE_MANAGER")
    public BaseResponseDTO<?> getAllTickets(@PathVariable Long projectId, Principal principal) {
        List<TicketModel> result = projectService.getAllTicketsByProjectId(projectId, principal);
        return BaseResponseDTO.ok(result);
    }

}
