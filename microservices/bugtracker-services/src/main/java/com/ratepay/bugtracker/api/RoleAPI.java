package com.ratepay.bugtracker.api;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.bugtracker.services.RoleService;
import com.ratepay.client.bugtracker.models.RoleModel;
import com.ratepay.core.rest.impl.BaseRestSqlModeImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
public class RoleAPI extends BaseRestSqlModeImpl<RoleModel, Long> {

    public RoleAPI(RoleService roleService) {
        super(roleService);
    }
}
