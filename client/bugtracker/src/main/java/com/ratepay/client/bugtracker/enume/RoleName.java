package com.ratepay.client.bugtracker.enume;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
public enum RoleName {
    ROLE_USER, ROLE_MANAGER, ROLE_DEVELOPER;

    public static RoleName getRoleName(String type) {
        switch (type.toLowerCase()) {
            case "role_user":
                return ROLE_USER;
            case "role_manager":
                return ROLE_MANAGER;
            default:
                return ROLE_USER;
        }
    }
}
