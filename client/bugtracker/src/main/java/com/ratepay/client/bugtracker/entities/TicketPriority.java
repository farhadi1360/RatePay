package com.ratepay.client.bugtracker.entities;

import com.ratepay.client.bugtracker.enume.TicketPriorityName;
import com.ratepay.core.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "ticket_priorities")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketPriority extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Enumerated(value = EnumType.STRING)
    private TicketPriorityName priority;

    @Override
    public String getSelectTitle() {
        return priority.name();
    }
}
