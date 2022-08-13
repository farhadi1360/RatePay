package com.ratepay.client.bugtracker.entities;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Data
@EqualsAndHashCode(callSuper = true)
public class Project extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String code;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User projectManager;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "project_developer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    private Set<User> developers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Ticket> tickets = new HashSet<>();
}
