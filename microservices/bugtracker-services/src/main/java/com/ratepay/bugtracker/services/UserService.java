package com.ratepay.bugtracker.services;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */

import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.models.UserModel;
import com.ratepay.core.dto.ResponseDto;
import com.ratepay.core.service.MainServiceSQLMode;

import java.io.Serializable;
import java.security.Principal;
import java.util.Optional;
import java.util.Set;

public interface UserService<M, ID extends Serializable> extends MainServiceSQLMode<M, ID> {

    Optional<User> findUserByPrincipal(Principal principal) throws EntityNotFoundException;

    Optional<User> findUserByUserIdAsDeveloper(Long developerId) throws EntityNotFoundException;


    UserModel findByUsername(String userName) throws EntityNotFoundException;

    ResponseDto deleteUser(Principal principal) throws EntityNotFoundException;

    Set<Project> getAllProjectsByDeveloper(Principal principal) throws EntityNotFoundException;

    Set<Project> getAllProjectsByDeveloperId(Long developerId) throws EntityNotFoundException;

    Set<Ticket> getAllTicketsForDeveloper(Long developerId) throws EntityNotFoundException;


}
