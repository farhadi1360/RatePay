package com.ratepay.client.bugtracker.mapper;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.core.mapper.BaseMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {TicketMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TicketMapper extends BaseMapper<TicketModel, Ticket> {
    @Mappings({
            @Mapping(target = "project", ignore = true),
            @Mapping(target = "developer", ignore = true)
    })
    TicketModel toModel(Ticket ticket);
}
