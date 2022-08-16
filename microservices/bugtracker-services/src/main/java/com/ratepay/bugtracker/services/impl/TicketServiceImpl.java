package com.ratepay.bugtracker.services.impl;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.querydsl.core.types.Predicate;
import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.exceptions.custom.IllegalActionException;
import com.ratepay.bugtracker.repository.TicketPriorityRepository;
import com.ratepay.bugtracker.repository.TicketRepository;
import com.ratepay.bugtracker.repository.TicketTypeRepository;
import com.ratepay.bugtracker.services.ProjectService;
import com.ratepay.bugtracker.services.TicketService;
import com.ratepay.bugtracker.services.UserService;
import com.ratepay.client.bugtracker.entities.*;
import com.ratepay.client.bugtracker.enume.TicketPriorityName;
import com.ratepay.client.bugtracker.enume.TicketTypeName;
import com.ratepay.client.bugtracker.mapper.ProjectMapper;
import com.ratepay.client.bugtracker.mapper.TicketMapper;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.core.dto.ResponseDto;
import com.ratepay.core.service.impl.MainServiceSQLModeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class TicketServiceImpl extends MainServiceSQLModeImpl<TicketModel, Ticket,Long> implements TicketService<TicketModel, Long> {
    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketPriorityRepository ticketPriorityRepository;
    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private static final ResponseDto DONE = new ResponseDto(true);

    public TicketServiceImpl(TicketRepository ticketRepository,TicketMapper ticketMapper,UserService userService,ProjectService projectService,ProjectMapper projectMapper,TicketTypeRepository ticketTypeRepository,TicketPriorityRepository ticketPriorityRepository){
        super(ticketMapper,ticketRepository);
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.ticketTypeRepository = ticketTypeRepository;
        this.ticketPriorityRepository=ticketPriorityRepository;
    }

    public ResponseDto createTicket(String projectCode,TicketModel ticketRequest,Principal principal)throws EntityNotFoundException{
        Optional<User> currentUser = userService.findUserByPrincipal(principal);
        if (currentUser.isPresent()) {
            Optional<ProjectModel> project = projectService.findByCode(projectCode);
            project.ifPresent(prj->{
                Ticket ticket = getTicketFromTicketModel(ticketRequest, currentUser.get(), projectMapper.toEntity(prj));
                Project projectEntity = projectMapper.toEntity(prj);
                projectEntity.addTicket(ticket);
                try{
                    ticketRepository.save(ticket);
                    projectService.save(projectEntity);
                }catch (Exception e){
                    log.error(e.getMessage());
                    throw new IllegalActionException("transaction does not doing");
                }
            });
        } else {
        throw new EntityNotFoundException("currentUser base on principal of request is not found!");
    }
        return DONE;
    }

    private Ticket getTicketFromTicketModel(TicketModel ticketModel, User author, Project project)throws EntityNotFoundException {
        TicketTypeName typeName;
        try {
            typeName = TicketTypeName.valueOf(ticketModel.getTicketType().getType());
        }catch (IllegalArgumentException exception){
            throw new EntityNotFoundException("Invalid ticket type");
        }
        TicketPriorityName priorityName;
        try {
            priorityName = TicketPriorityName.valueOf(ticketModel.getTicketPriority().getPriority().name().toUpperCase());
        }catch (IllegalArgumentException exception){
            throw new EntityNotFoundException("Invalid ticket priority");
        }
        TicketType ticketType = ticketTypeRepository.findByType(typeName)
                .orElseThrow(() -> new EntityNotFoundException("Invalid ticket type"));
        TicketPriority ticketPriority = ticketPriorityRepository.findByPriority(priorityName)
                .orElseThrow(() -> new EntityNotFoundException("Invalid ticket priority"));
        return new Ticket(ticketModel.getTitle(), ticketModel.getDescription(),
                new Timestamp(System.currentTimeMillis()), author, ticketType, ticketPriority, project);
    }
}
