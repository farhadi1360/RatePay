package com.ratepay.client.bugtracker.mapper;

import com.ratepay.client.bugtracker.entities.TicketPriority;
import com.ratepay.client.bugtracker.models.TicketPriorityModel;
import com.ratepay.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {TicketPriorityMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TicketPriorityMapper extends BaseMapper<TicketPriorityModel, TicketPriority> {
}
