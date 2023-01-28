package com.projectmanager.manager.user;

public enum UserPermission {
    READ("read"),
    WRITE("write");

    private String perm;

    UserPermission(String perm) {
        this.perm = perm;
    }

    public String getPerm() {
        return perm;
    }
}
