package com.ratepay.bugtracker.services;

import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.repository.RoleRepository;
import com.ratepay.client.bugtracker.entities.Role;
import com.ratepay.client.bugtracker.enume.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByRoleName(RoleName roleName){
        return roleRepository.findByRole(roleName)
                .orElseThrow(
                        () -> new EntityNotFoundException("Role " + roleName.name() + " doesn't exist")
                );
    }
}
