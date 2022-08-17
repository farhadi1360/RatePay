package com.ratepay.bugtracker.utils;

import com.ratepay.bugtracker.repository.TicketRepository;
import com.ratepay.bugtracker.repository.UserRepository;
import com.ratepay.client.bugtracker.entities.Ticket;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.client.bugtracker.models.UserModel;

import java.util.*;

public final class ProjectUtils {
    private ProjectUtils(){}


    public static List<User> getAllDeveloper(Set<UserModel> userModels , UserRepository userRepository){
        List<User> developers = new ArrayList<>();
        if(userModels!=null && userModels.size()>0){
            userModels.forEach(user->{
                Optional<User> developer = userRepository.findById(user.getId());
                developer.ifPresent(dev->{
                    developers.add(dev);
                });
            });
        }
        return developers;
    }

    public static List<Ticket> getAllTicket(Set<TicketModel> ticketModels , TicketRepository ticketRepository) {
        List<Ticket> tickets=new ArrayList<>();
        if(ticketModels!=null && ticketModels.size()>0){
            ticketModels.stream().forEach(ticketModel -> {
                Optional<Ticket> ticket = ticketRepository.findById(ticketModel.getId());
                ticket.ifPresent(tic->{
                    tickets.add(tic);
                });
            });
        }

        return tickets;
    }
}
