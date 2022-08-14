package com.ratepay.bugtracker.services;

import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.repository.ProjectRepository;
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.mapper.ProjectMapper;
import com.ratepay.client.bugtracker.mapper.UserMapper;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.client.bugtracker.models.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    private final UserMapper userMapper;


    public ProjectModel projectFromRequest(ProjectModel projectRequest, UserModel projectManager) {
        Project project = new Project(projectRequest.getName(), userMapper.toEntity(projectManager));
        return projectMapper.toModel(project);
    }


    public void save(ProjectModel project) {

        projectRepository.save(projectMapper.toEntity(project));
    }

    public ProjectModel findById(Long projectId) {
        return projectMapper.toModel(projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Project with id " + projectId + " doesn't exist!")
        ));
    }

    public void delete(ProjectModel project) {
        projectRepository.delete(projectMapper.toEntity(project));
    }

    public ProjectModel findByCode(String code) {
        return projectMapper.toModel(projectRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException("Project with code " + code + " doesn't exist!")
        ));
    }
}
