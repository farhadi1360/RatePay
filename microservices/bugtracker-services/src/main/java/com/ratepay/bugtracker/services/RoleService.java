package com.ratepay.bugtracker.services;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.client.bugtracker.enume.RoleName;
import com.ratepay.client.bugtracker.models.RoleModel;
import com.ratepay.core.service.MainServiceSQLMode;

import java.io.Serializable;

public interface RoleService <M, ID extends Serializable> extends MainServiceSQLMode<M, ID> {
    RoleModel findByRoleName(RoleName roleName);
}
