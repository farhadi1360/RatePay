package com.ratepay.client.bugtracker.mapper;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.client.bugtracker.entities.TicketType;
import com.ratepay.client.bugtracker.models.TicketTypeModel;
import com.ratepay.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {TicketTypeMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TicketTypeMapper extends BaseMapper<TicketTypeModel, TicketType> {
}
