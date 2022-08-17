package com.ratepay.client.bugtracker.models;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.core.model.BaseModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectModel extends BaseModel {
    Long id;
    @NotBlank
    String name;
    @NotBlank
    String code;
    UserModel projectManager;
    @NotNull
    Set<UserModel> developers;

    Set<TicketModel> tickets;

public ProjectModel(String projectName,UserModel projectManager) {
    this.name = projectName;
    this.projectManager = projectManager;
}
}
