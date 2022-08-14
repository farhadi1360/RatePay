package com.ratepay.client.bugtracker.models;

import com.ratepay.client.bugtracker.enume.TicketPriorityName;
import com.ratepay.core.model.BaseModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketPriorityModel extends BaseModel {
    private TicketPriorityName priority;
}
