package com.ratepay.bugtracker.services;

import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.repository.TicketPriorityRepository;
import com.ratepay.bugtracker.repository.TicketRepository;
import com.ratepay.bugtracker.repository.TicketTypeRepository;
import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.client.bugtracker.entities.TicketPriority;
import com.ratepay.client.bugtracker.entities.TicketType;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.enume.TicketPriorityName;
import com.ratepay.client.bugtracker.enume.TicketTypeName;
import com.ratepay.client.bugtracker.mapper.*;
import com.ratepay.client.bugtracker.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketMapper ticketMapper;
    private final TicketTypeMapper ticketTypeMapper;
    private final TicketPriorityMapper ticketPriorityMapper;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketPriorityRepository ticketPriorityRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;

    public TicketModel getTicketFromRequest(TicketModel ticketRequest, UserModel author, ProjectModel project) {
        TicketTypeName typeName;
        try {
            typeName = TicketTypeName.valueOf(ticketRequest.getTicketType().getType());
        } catch (IllegalArgumentException exception) {
            throw new EntityNotFoundException("Invalid ticket type");
        }

        TicketPriorityName priorityName;
        try {
            priorityName = TicketPriorityName.valueOf(ticketRequest.getTicketPriority().getPriority().name());
        } catch (IllegalArgumentException exception) {
            throw new EntityNotFoundException("Invalid ticket priority");
        }

        TicketType ticketType = ticketTypeRepository.findByType(typeName)
                .orElseThrow(() -> new EntityNotFoundException("Invalid ticket type"));
        TicketPriority ticketPriority = ticketPriorityRepository.findByPriority(priorityName)
                .orElseThrow(() -> new EntityNotFoundException("Invalid ticket priority"));
        Ticket ticket = new Ticket(ticketRequest.getTitle(), ticketRequest.getDescription(),
                new Timestamp(ticketRequest.getTimestamp().getTime()), userMapper.toEntity(author), ticketType, ticketPriority, projectMapper.toEntity(project));
        return ticketMapper.toModel(ticket);
    }

    public void save(TicketModel ticket) {
        ticketRepository.save(ticketMapper.toEntity(ticket));
    }

    public TicketModel findById(Long ticketId) {
        return ticketMapper.toModel(ticketRepository.findById(ticketId).orElseThrow(
                () -> new EntityNotFoundException("Ticket with id " + ticketId + " doesn't exist!")
        ));
    }
}
