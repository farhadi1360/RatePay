package com.ratepay.bugtracker.services.impl;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */

import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.exceptions.custom.IllegalActionException;
import com.ratepay.bugtracker.repository.*;
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
import com.ratepay.client.bugtracker.models.TicketRequest;
import com.ratepay.core.dto.ResponseDto;
import com.ratepay.core.service.impl.MainServiceSQLModeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Optional;

@Service
@Slf4j
public class TicketServiceImpl extends MainServiceSQLModeImpl<TicketModel, Ticket, Long> implements TicketService<TicketModel, Long> {
    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketPriorityRepository ticketPriorityRepository;
    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private static final ResponseDto DONE = new ResponseDto(true);

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper, UserService userService, ProjectService projectService, ProjectMapper projectMapper, TicketTypeRepository ticketTypeRepository, TicketPriorityRepository ticketPriorityRepository,ProjectRepository projectRepository,UserRepository userRepository) {
        super(ticketMapper, ticketRepository);
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.ticketTypeRepository = ticketTypeRepository;
        this.ticketPriorityRepository = ticketPriorityRepository;
        this.projectRepository = projectRepository;
        this.userRepository=userRepository;
    }

    public ResponseDto createTicket(String projectCode, TicketRequest ticketRequest, Principal principal) throws EntityNotFoundException {
        Optional<User> currentUser = userService.findUserByPrincipal(principal);
        if (currentUser.isPresent()) {
            Optional<Project> project = projectRepository.findByCode(projectCode);
            project.ifPresent(prj -> {
                Ticket ticket = getTicketFromTicketModel(ticketRequest, currentUser.get(),prj);
                prj.addTicket(ticket);
                try {
                    ticketRepository.save(ticket);
                    projectRepository.save(prj);
                    log.info("Ticket with name of {} was successfully created", ticket.getTitle());
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new IllegalActionException("transaction does not doing");
                }
            });
        } else {
            throw new EntityNotFoundException("currentUser base on principal of request is not found!");
        }
        return DONE;
    }


    public ResponseDto assignTicketToDeveloper(Long ticketId, Long developerId, Principal principal) throws EntityNotFoundException, IllegalActionException {
        Optional<User> projectManager = userService.findUserByPrincipal(principal);
        if (projectManager.isPresent()) {
            Optional<User> developer = userService.findUserByUserIdAsDeveloper(developerId);
            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
            ticket.ifPresentOrElse((tick) -> {
                        Project project = tick.getProject();
                        developer.ifPresentOrElse((dev) -> {
                            if (!dev.getProjectsWorkingOn().contains(project)) {
                                throw new IllegalActionException("This developer isn't assigned to this project");
                            }
                            tick.setDeveloper(dev);
                            dev.addTicketsWorkingOn(tick);
                            try {
                                ticketRepository.save(tick);
                                userRepository.save(dev);
                                log.info("the {} Ticket successfully assigned to {} as developer ", tick.getTitle(), dev.getUsername());
                            } catch (Exception e) {
                                log.error(e.getMessage());
                                throw new IllegalActionException("transaction does not doing");
                            }

                        }, () -> {
                            throw new EntityNotFoundException("Developer with id " + developerId + " doesn't exist");
                        });
                    },
                    () -> {
                        throw new EntityNotFoundException("Ticket with id " + ticketId + " doesn't exist");
                    }
            );
        } else {
            throw new EntityNotFoundException("ProjectManager base on principal of request is not found!");
        }
        return DONE;
    }

    @Override
    public ResponseDto removeDeveloperFromTicket(Long ticketId, Long developerId, Principal principal) throws EntityNotFoundException, IllegalActionException {
        Optional<User> projectManager = userService.findUserByPrincipal(principal);
        if (projectManager.isPresent()) {
            Optional<User> developer = userService.findUserByUserIdAsDeveloper(developerId);
            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
            ticket.ifPresentOrElse((tick) -> {
                        Project project = tick.getProject();
                        if (!project.getProjectManager().equals(projectManager)) {
                            throw new EntityNotFoundException("projectManager is not equal to  current user");
                        }
                        developer.ifPresentOrElse((dev) -> {
                            if (!tick.getDeveloper().equals(dev)) {
                                throw new IllegalActionException("This developer is not assigned to this ticket!");
                            }
                            dev.removeTicketsWorkingOn(tick);
                            tick.setDeveloper(null);
                            try {
                                userService.save(dev);
                                ticketRepository.save(tick);
                                log.info("The {} Developer removed from {} Ticket", dev.getUsername(), tick.getTitle());
                            } catch (Exception e) {
                                log.error(e.getMessage());
                                throw new IllegalActionException("transaction does not doing");
                            }

                        }, () -> {
                            throw new EntityNotFoundException("Developer with id " + developerId + " not found!");
                        });
                    }, () -> {
                        throw new EntityNotFoundException("Ticket with id " + ticketId + " doesn't exist");
                    }
            );
        } else {
            throw new EntityNotFoundException("ProjectManager base on principal of request is not found!");
        }
        return DONE;
    }

    private Ticket getTicketFromTicketModel(TicketRequest ticketModel, User author, Project project) throws EntityNotFoundException {
        TicketTypeName typeName;
        try {
            typeName = TicketTypeName.valueOf(ticketModel.getType());
        } catch (IllegalArgumentException exception) {
            throw new EntityNotFoundException("Invalid ticket type");
        }
        TicketPriorityName priorityName;
        try {
            priorityName = TicketPriorityName.valueOf(ticketModel.getPriority().toUpperCase());
        } catch (IllegalArgumentException exception) {
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
