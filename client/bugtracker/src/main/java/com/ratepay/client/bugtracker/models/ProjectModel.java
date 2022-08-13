package com.ratepay.client.bugtracker.models;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
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
public class ProjectModel extends BaseModel {
    Long id;
    String name;
    String code;
    UserModel projectManager;
    Set<UserModel> developers;
    Set<TicketModel> tickets;

}
