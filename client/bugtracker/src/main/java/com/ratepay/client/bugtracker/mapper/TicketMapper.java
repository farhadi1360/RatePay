package com.ratepay.client.bugtracker.mapper;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper extends BaseMapper<TicketModel, Ticket> {

}