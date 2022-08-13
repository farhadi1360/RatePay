package com.ratepay.client.bugtracker.models;
/**
 * @author Mustafa Farhadi
 * @email farhadi.kam@gmail.com
 */

import com.ratepay.core.model.BaseModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserModel extends BaseModel {

    Long id;
    String userName;
    String email;
    String password;
    String firstName;
    String lastName;
    Boolean active;
    Set<RoleModel> roles;
    Set<ProjectModel> projects;
    Set<ProjectModel> projectsWorkingOn;
    Set<TicketModel> ticketsWorkingOn;
}
