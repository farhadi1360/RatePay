package com.ratepay.bugtracker.repository;

import com.ratepay.client.bugtracker.entities.Role;
import com.ratepay.client.bugtracker.enume.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleName roleName);
}
