package com.ratepay.bugtracker.utils;

import com.ratepay.client.bugtracker.entities.Role;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.enume.RoleName;
import com.ratepay.client.bugtracker.models.RegisterRequest;
import com.ratepay.core.dto.ResponseDto;

import java.util.HashSet;
import java.util.Set;

public final class UserPrepareInfo {
    private static final ResponseDto DONE = new ResponseDto(true);

    private UserPrepareInfo() {
    }

    public static ResponseDto mockAnswer() {
        return DONE;
    }

    public static RegisterRequest userRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("ratepay1");
        request.setPassword("123");
        request.setEmail("test@ratepay.com");
        Set roles = new HashSet();
        roles.add("role_user");
        request.setRoles(roles);
        return request;
    }

    public static User getUser() {
        User user = new User();
        user.setId(1l);
        user.setUsername("ratepay1");
        user.setPassword("$2a$10$vkmPHBa3Fd4pl3Ue/XV0Ve2YcpOHf2.oHg6KjtyaNjJMCLh/Ww1HK");
        user.setEmail("test@ratepay.com");
        Role userRole = new Role();
        userRole.setRole(RoleName.ROLE_USER);
        Role developerRole = new Role();
        developerRole.setRole(RoleName.ROLE_DEVELOPER);
        Role managerRole = new Role();
        managerRole.setRole(RoleName.ROLE_MANAGER);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(developerRole);
        roles.add(managerRole);
        user.setRoles(roles);
        return user;
    }

    public static User getDeveloper(){
        User user = new User();
        user.setId(2l);
        user.setUsername("ratepay2");
        user.setPassword("$2a$10$vkmPHBa3Fd4pl3Ue/XV0Ve2YcpOHf2.oHg6KjtyaNjJMCLh/Ww1HK");
        user.setEmail("test2@ratepay.com");
        Role developerRole = new Role();
        developerRole.setRole(RoleName.ROLE_DEVELOPER);
        Set<Role> roles = new HashSet<>();
        roles.add(developerRole);
        user.setRoles(roles);
        return user;
    }

}
