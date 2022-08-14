package com.ratepay.bugtracker.repository;

import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.core.repository.BaseSQLRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends BaseSQLRepository<Ticket, Long> {
}
