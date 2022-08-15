package com.ratepay.client.bugtracker.entities;
/**
 * @author Mustafa Farhadi
 * @email farhadi.kam@gmail.com
 */

import com.ratepay.core.entity.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Project extends BaseEntity<Long> {
    public Project(String name, User projectManager) {
        this.name = name;
        this.projectManager = projectManager;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User projectManager;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "project_developer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> developers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<Ticket> tickets = new ArrayList<>();

    private String generateCode() {
        int length = 6;
        return RandomStringUtils.random(length, true, true);
    }

    public void addDeveloper(User developer) {
        developers.add(developer);
    }

    public void removeDeveloper(User developer) {
        developers.remove(developer);
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    @Override
    public String getSelectTitle() {
        return name;
    }
}
