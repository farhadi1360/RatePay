package com.ratepay.bugtracker.services.impl;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.querydsl.core.types.Predicate;
import com.ratepay.bugtracker.repository.ProjectRepository;
import com.ratepay.bugtracker.services.ProjectService;
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.mapper.ProjectMapper;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.core.service.impl.MainServiceSQLModeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ProjectServiceImpl extends MainServiceSQLModeImpl<ProjectModel, Project,Long> implements ProjectService<ProjectModel, Long> {
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository,ProjectMapper projectMapper) {
        super(projectMapper, projectRepository);
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
    }

    @Override
    public Predicate queryBuilder(ProjectModel filter) {
        return null;
    }
}
