package com.ratepay.client.bugtracker.models;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ratepay.client.bugtracker.entities.TicketPriority;
import com.ratepay.core.model.BaseModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketModel extends BaseModel {

    Long id;
    @NotBlank
    String title;
    String description;
    @JsonIgnore
    Timestamp timestamp;
    UserModel author;
    @NotNull
    TicketTypeModel ticketType;
    @NotNull
    TicketPriorityModel ticketPriority;
    ProjectModel project;
    UserModel developer;

    public TicketModel(String title, String description, Timestamp timestamp, UserModel author, TicketTypeModel ticketType, TicketPriorityModel ticketPriority, ProjectModel project) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.ticketType = ticketType;
        this.ticketPriority = ticketPriority;
        this.project = project;
    }
}
