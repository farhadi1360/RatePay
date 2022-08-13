package com.ratepay.client.bugtracker.entities;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */

import com.ratepay.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true)
    @NotNull
    private String userName;

    @Column(name = "email", unique = true)
    @NotNull
    @Email
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "active_flag")
    private Boolean active;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectManager")
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "developers")
    private Set<Project> projectsWorkingOn = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "developer")
    private Set<Ticket> ticketsWorkingOn = new HashSet<>();

}
