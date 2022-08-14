package com.ratepay.client.bugtracker.entities;

import com.ratepay.client.bugtracker.enume.TicketPriorityName;
import com.ratepay.core.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "ticket_priorities")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketPriority extends BaseEntity {
    @Enumerated(value = EnumType.STRING)
    private TicketPriorityName priority;
}
