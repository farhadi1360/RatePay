package com.ratepay.bugtracker.services.impl;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */

import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.repository.UserRepository;
import com.ratepay.bugtracker.services.TicketService;
import com.ratepay.bugtracker.services.UserService;
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.mapper.UserMapper;
import com.ratepay.client.bugtracker.models.UserModel;
import com.ratepay.core.dto.ResponseDto;
import com.ratepay.core.service.impl.MainServiceSQLModeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl extends MainServiceSQLModeImpl<UserModel, User, Long> implements UserService<UserModel, Long> {

    private static final ResponseDto DONE = new ResponseDto(true);
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        super(userMapper, userRepository);
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }


    @Override
    public UserModel findByUsername(String userName) throws EntityNotFoundException {
        return
                userMapper.toModel(userRepository.findByUsername(userName).orElseThrow(()
                        -> new EntityNotFoundException("Username " + userName + " not found!")));
    }

    public ResponseDto deleteUser(Principal principal) throws EntityNotFoundException {
        Optional<User> user = findUserByPrincipal(principal);
        if (user.isPresent()) {
            user.get().getProjectsWorkingOn().stream().forEach(project -> {
                project.getDevelopers().stream().forEach(usr->{
                    project.getDevelopers().remove(user);
                });
            });
            user.get().getTicketsWorkingOn().stream().forEach(ticket -> {
                ticket.setDeveloper(null);
            });
            userRepository.delete(user.get());
            log.info("User {} was deleted successfully", user.get().getUsername());
            return DONE;
        } else {
            throw  new EntityNotFoundException("Username " + principal.getName() + " not found!");
        }

    }


    public Set<Project> getAllProjectsByDeveloper(Principal principal) throws EntityNotFoundException {
        Optional<User> userAsDeveloper = findUserByPrincipal(principal);
       if(userAsDeveloper.isPresent()){
            return userAsDeveloper.get().getProjectsWorkingOn();
        }else{
            throw new EntityNotFoundException("Username " + principal.getName() + " not found!");
        }

    }

    @Override
    public Set<Project> getAllProjectsByDeveloperId(Long developerId) throws EntityNotFoundException {
        Optional<User> userAsDeveloper = findUserByUserIdAsDeveloper(developerId);
        if(userAsDeveloper.isPresent()){
            return userAsDeveloper.get().getProjectsWorkingOn();
        }else{
            throw new EntityNotFoundException("developerId  " + developerId + " not found!");
        }
    }

    public Set<Ticket> getAllTicketsForDeveloper(Long developerId) throws EntityNotFoundException {
        Optional<User> userAsDeveloper = findUserByUserIdAsDeveloper(developerId);
        if(userAsDeveloper.isPresent()){
            return userAsDeveloper.get().getTicketsWorkingOn();
        }else{
            throw new EntityNotFoundException("developerId  " + developerId + " not found!");
        }
    }

    public Optional<User> findUserByUserIdAsDeveloper(Long developerId)throws EntityNotFoundException {
        return Optional.ofNullable(userRepository.findById(developerId).orElseThrow(() -> new EntityNotFoundException("developerId " + developerId + " not found!")));
    }

    public Optional<User> findUserByPrincipal(Principal principal) throws EntityNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(principal.getName()).orElseThrow(() -> new EntityNotFoundException("Username " + principal.getName() + " not found!")));
    }
}
