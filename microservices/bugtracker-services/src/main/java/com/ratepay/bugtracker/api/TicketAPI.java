package com.ratepay.bugtracker.api;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */

import com.ratepay.bugtracker.services.TicketService;
import com.ratepay.client.bugtracker.models.TicketModel;
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
    public BaseResponseDTO<?> addTicket(@RequestParam(name = "code") String projectCode,
                                        @RequestBody @Valid TicketModel ticketModel,
                                        Principal principal) {
        ResponseDto result = ticketService.createTicket(projectCode, ticketModel, principal);
        return BaseResponseDTO.ok(result);
    }


}
