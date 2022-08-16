package com.ratepay.bugtracker.services;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.core.dto.ResponseDto;
import com.ratepay.core.exception.GeneralException;
import com.ratepay.core.service.MainServiceSQLMode;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProjectService <M, ID extends Serializable> extends MainServiceSQLMode<M, ID> {
    ResponseDto createProject(ProjectModel projectModel, Principal principal) throws EntityNotFoundException;

    ResponseDto assignDeveloperToProject(Long projectId, Long developerId, Principal principal) throws EntityNotFoundException, GeneralException;

    ResponseDto removeDeveloperFromProject(Long projectId, Long developerId, Principal principal) throws EntityNotFoundException;

    List<TicketModel> getAllTicketsByProjectId(Long projectId, Principal principal) throws EntityNotFoundException;

    Optional<ProjectModel> findByCode(String code) throws EntityNotFoundException;
}
