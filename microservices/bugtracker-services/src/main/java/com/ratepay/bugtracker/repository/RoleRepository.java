package com.ratepay.bugtracker.repository;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.client.bugtracker.entities.Role;
import com.ratepay.client.bugtracker.enume.RoleName;
import com.ratepay.core.repository.BaseSQLRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseSQLRepository<Role, Long> {
    Optional<Role> findByRole(RoleName roleName);
}
