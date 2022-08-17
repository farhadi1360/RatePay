package com.ratepay.bugtracker.repository;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.client.bugtracker.entities.TicketPriority;
import com.ratepay.client.bugtracker.enume.TicketPriorityName;
import com.ratepay.core.repository.BaseSQLRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketPriorityRepository extends BaseSQLRepository<TicketPriority, Long> {
    Optional<TicketPriority> findByPriority(TicketPriorityName priorityName);
}
