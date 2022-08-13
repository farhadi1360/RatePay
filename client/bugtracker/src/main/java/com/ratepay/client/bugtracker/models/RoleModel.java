package com.ratepay.client.bugtracker.models;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.core.model.BaseModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleModel extends BaseModel {
     Long id;
     String role;

}
