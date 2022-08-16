package com.ratepay.client.bugtracker.mapper;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.core.mapper.BaseMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {ProjectMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProjectMapper extends BaseMapper<ProjectModel, Project> {
    @Mappings({
            @Mapping(target = "developers", ignore = true),
            @Mapping(target = "projectManager", ignore = true),
            @Mapping(target = "tickets", ignore = true)
    })
    ProjectModel toModel(Project project);
}
