package com.ratepay.client.bugtracker.mapper;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.entities.Role;
import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.client.bugtracker.models.RoleModel;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.client.bugtracker.models.UserModel;
import com.ratepay.core.mapper.BaseMapper;
import org.mapstruct.*;

import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {UserMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper extends BaseMapper<UserModel, User> {

    @Override
    @Mappings({
            @Mapping(target = "projects", ignore = true),
            @Mapping(target = "projectsWorkingOn", ignore = true),
            @Mapping(target = "ticketsWorkingOn", ignore = true),
            @Mapping(target = "roles", ignore = true)
//            @Mapping(target = "ticketsWorkingOn", qualifiedByName = "ticketToTicketModel")
    })
    UserModel toModel(User user);

//    @Named("projectToProjectModel")
//    @Mapping(target = "projectManager",ignore = true)
//    Set<ProjectModel> toModel(Set<Project> projects);

//    @Named("ticketToTicketModel")
//    @Mapping(target = "developer", ignore = true)
//    Set<TicketModel> toModel(Set<Ticket> tickets);

}
