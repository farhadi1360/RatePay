package com.ratepay.bugtracker.services;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.exceptions.custom.IllegalActionException;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.core.dto.ResponseDto;
import com.ratepay.core.service.MainServiceSQLMode;

import java.io.Serializable;
import java.security.Principal;

public interface TicketService <M, ID extends Serializable> extends MainServiceSQLMode<M, ID> {
    ResponseDto createTicket(String projectCode, TicketModel ticketRequest, Principal principal)throws EntityNotFoundException;

    ResponseDto assignTicketToDeveloper(Long ticketId, Long developerId, Principal principal) throws EntityNotFoundException, IllegalActionException;

    ResponseDto removeDeveloperFromTicket(Long ticketId, Long developerId, Principal principal) throws EntityNotFoundException, IllegalActionException;


}
