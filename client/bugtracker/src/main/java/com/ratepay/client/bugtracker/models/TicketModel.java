package com.ratepay.client.bugtracker.models;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */

import com.ratepay.client.bugtracker.enume.TicketPriority;
import com.ratepay.core.model.BaseModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketModel extends BaseModel {

    Long id;
    String title;
    String description;
    Timestamp timestamp;
    UserModel author;
    TicketTypeModel type;
    TicketPriority priority;
    ProjectModel project;
    UserModel developer;
}
