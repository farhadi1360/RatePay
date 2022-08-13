package com.ratepay.client.bugtracker.entities;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.client.bugtracker.enume.TicketTypeName;
import com.ratepay.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "ticket_types")
@Data
@EqualsAndHashCode(callSuper = true)
public class TicketType extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Enumerated(value = EnumType.STRING)
    private TicketTypeName type;
}
