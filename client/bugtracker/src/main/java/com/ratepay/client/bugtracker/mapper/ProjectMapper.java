package com.ratepay.client.bugtracker.mapper;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper extends BaseMapper<ProjectModel, Project> {
}
