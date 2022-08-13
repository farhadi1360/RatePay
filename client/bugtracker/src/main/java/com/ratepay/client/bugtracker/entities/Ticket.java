package com.ratepay.client.bugtracker.entities;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.client.bugtracker.enume.TicketPriority;
import com.ratepay.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tickets")
@Data
@EqualsAndHashCode(callSuper = true)
public class Ticket extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User author;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TicketType type;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TicketPriority priority;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Project project;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User developer;
}
