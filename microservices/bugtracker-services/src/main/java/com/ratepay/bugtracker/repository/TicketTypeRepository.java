package com.ratepay.bugtracker.repository;

import com.ratepay.client.bugtracker.entities.TicketType;
import com.ratepay.client.bugtracker.enume.TicketTypeName;
import com.ratepay.core.repository.BaseSQLRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketTypeRepository extends BaseSQLRepository<TicketType, Long> {
    Optional<TicketType> findByType(TicketTypeName typeName);
}
