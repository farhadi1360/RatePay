package com.ratepay.bugtracker.services.impl;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.querydsl.core.types.Predicate;
import com.ratepay.bugtracker.repository.TicketRepository;
import com.ratepay.bugtracker.services.TicketService;
import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.client.bugtracker.mapper.TicketMapper;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.core.service.impl.MainServiceSQLModeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketServiceImpl extends MainServiceSQLModeImpl<TicketModel, Ticket,Long> implements TicketService<TicketModel, Long> {
    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository,TicketMapper ticketMapper){
        super(ticketMapper,ticketRepository);
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
    }
    @Override
    public Predicate queryBuilder(TicketModel filter) {
        return null;
    }
}
