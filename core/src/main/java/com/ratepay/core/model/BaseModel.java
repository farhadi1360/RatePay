package com.ratepay.core.model;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseModel<ID extends Serializable> implements Serializable {
    @JsonIgnore
    protected String createdBy;
    @JsonIgnore
    protected Date createdDate;
    @JsonIgnore
    protected String modifiedBy;
    @JsonIgnore
    protected Date modifiedDate;
    @JsonIgnore
    protected int version;

}
