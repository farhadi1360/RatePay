package com.ratepay.bugtracker.utils;

import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.enume.RoleName;
import com.ratepay.client.bugtracker.enume.TicketPriorityName;
import com.ratepay.client.bugtracker.enume.TicketTypeName;
import com.ratepay.client.bugtracker.mapper.UserMapper;
import com.ratepay.client.bugtracker.models.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ProjectPrepareInfo {
    private ProjectPrepareInfo(){}


    public static ProjectModel prepareProjectModelInfo(UserMapper userMapper) {
        ProjectModel projectModel =new ProjectModel() ;
        UserModel userModel = new UserModel();
        RoleModel developerRole = new RoleModel();
        developerRole.setRole(RoleName.ROLE_DEVELOPER.name());
        RoleModel userRole = new RoleModel();
        userRole.setRole(RoleName.ROLE_USER.name());
        Set<RoleModel> roles = new HashSet<>();
        Set<UserModel> userModels = new HashSet<>();
        roles.add(developerRole);
        roles.add(userRole);
        userModel.setActive(true);
        userModel.setFirstName("Mustafa");
        userModel.setLastName("Farhadi");
        userModel.setEmail("farhadi.kam@gmail.com");
        userModel.setId(1l);
        userModel.setUserName("m.farhadi");
        userModel.setPassword("123");
        userModel.setRoles(roles);
        userModels.add(userModel);
        projectModel.setId(1l);
        projectModel.setName("BugTracker");
        projectModel .setCode("100");
        projectModel.setId(3l);
        User userManager = UserPrepareInfo.getUser();
        UserModel productManager = userMapper.toModel(userManager);
        Set<RoleModel> roleOfManager = new HashSet<>();
        RoleModel role1 = new RoleModel();
        role1.setRole(RoleName.ROLE_USER.name());
        RoleModel role2 = new RoleModel();
        role2.setRole(RoleName.ROLE_DEVELOPER.name());
        RoleModel role3 = new RoleModel();
        role3.setRole(RoleName.ROLE_MANAGER.name());
        roleOfManager.add(role1);
        roleOfManager.add(role2);
        roleOfManager.add(role3);
        productManager.setRoles(roleOfManager);
        productManager.setUserName(userManager.getUsername());
        projectModel.setProjectManager(productManager);
        projectModel .setDevelopers(userModels);
        return projectModel;
    }



public static List<TicketModel> getAllMockTicketModel(){
    List<TicketModel> result = new ArrayList<>();
    TicketModel ticketModel = new TicketModel();
    ticketModel.setId(1l);
    TicketTypeModel ticketTypeModel = new TicketTypeModel();
    ticketTypeModel.setId(1l);
    ticketTypeModel.setType(TicketTypeName.BUG.name());
    ticketModel.setTicketType(ticketTypeModel);
    TicketPriorityModel ticketPriorityModel = new TicketPriorityModel();
    ticketPriorityModel.setPriority(TicketPriorityName.HIGH);
    ticketModel.setTicketPriority(ticketPriorityModel);
    ticketModel.setTitle("paymaent is not working");
    ticketModel.setDescription("when I use payment API it does not woek .");
    result.add(ticketModel);
    return result;
}

public static ProjectModel getSimpleProjectModelForAPTTest(){
    ProjectModel projectModel =new  ProjectModel();
    projectModel.setName("BugTracker");
    projectModel.setCode("120");
    UserModel developer = new UserModel();
    developer.setId(1l);
    Set<UserModel> developers = new HashSet<>();
    developers.add(developer);
    projectModel.setDevelopers(developers);
    return projectModel;
}
}
