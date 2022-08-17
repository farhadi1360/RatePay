package com.ratepay.bugtracker.api;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */

import com.ratepay.bugtracker.services.TicketService;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.client.bugtracker.models.TicketRequest;
import com.ratepay.core.dto.BaseResponseDTO;
import com.ratepay.core.dto.ResponseDto;
import com.ratepay.core.rest.impl.BaseRestSqlModeImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketAPI extends BaseRestSqlModeImpl<TicketModel, Long> {
    private final TicketService ticketService;

    public TicketAPI(TicketService ticketService) {
        super(ticketService);
        this.ticketService = ticketService;
    }


    @PostMapping("/create")
    @Secured("ROLE_USER")
    public BaseResponseDTO<?> createTicket(@RequestParam(name = "code") String projectCode,
                                        @RequestBody @Valid TicketRequest ticketRequest,
                                        Principal principal) {
        ResponseDto result = ticketService.createTicket(projectCode, ticketRequest, principal);
        return BaseResponseDTO.ok(result);
    }
    @PutMapping("{ticketId}/assign-developer")
    @Secured("ROLE_DEVELOPER")
    public BaseResponseDTO<?> assignTicketToDeveloper(@PathVariable Long ticketId,
                                                     @RequestParam(name = "developerId") Long developerId,
                                                     Principal principal){
        ResponseDto result = ticketService.assignTicketToDeveloper(ticketId, developerId, principal);
        return BaseResponseDTO.ok(result);
    }
    @DeleteMapping("/{ticketId}/remove")
    @Secured("ROLE_MANAGER")
    public BaseResponseDTO<?> removeDeveloperFromTicket(@PathVariable Long ticketId,
                                                      @RequestParam(name = "developerId") Long developerId,
                                                      Principal principal){
        ResponseDto result = ticketService.removeDeveloperFromTicket(ticketId, developerId, principal);
        return BaseResponseDTO.ok(result);
    }
}
