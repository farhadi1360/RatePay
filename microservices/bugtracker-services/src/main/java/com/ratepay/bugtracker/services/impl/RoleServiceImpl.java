package com.ratepay.bugtracker.services.impl;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.querydsl.core.types.Predicate;
import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.repository.RoleRepository;
import com.ratepay.bugtracker.services.RoleService;
import com.ratepay.client.bugtracker.entities.Role;
import com.ratepay.client.bugtracker.enume.RoleName;
import com.ratepay.client.bugtracker.mapper.RoleMapper;
import com.ratepay.client.bugtracker.models.RoleModel;
import com.ratepay.core.service.impl.MainServiceSQLModeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl extends MainServiceSQLModeImpl<RoleModel, Role,Long> implements RoleService<RoleModel, Long> {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    public RoleServiceImpl(RoleRepository roleRepository,RoleMapper roleMapper) {
        super(roleMapper,roleRepository);
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }
    public RoleModel findByRoleName(RoleName roleName){
        return roleMapper.toModel(roleRepository.findByRole(roleName)
                .orElseThrow(
                        () -> new EntityNotFoundException("Role " + roleName.name() + " doesn't exist")
                ));
    }
    @Override
    public Predicate queryBuilder(RoleModel filter) {
        return null;

    }
}
