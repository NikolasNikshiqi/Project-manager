package com.projectmanager.manager.user;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public enum UserRole {
    ADMIN(EnumSet.of(UserPermission.WRITE,UserPermission.READ)),
    USER(EnumSet.of(UserPermission.READ));

    private Set<UserPermission> permissions ;

     UserRole (Set<UserPermission> permissions){
         this.permissions = permissions;
     }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }
}
