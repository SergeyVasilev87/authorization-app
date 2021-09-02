package com.github.SergeyVasilev87.first.entity;

/**
 * Содержит права для ролей (чтение, запись и т.д.)
 */

public enum Permission {
    READ ("only_read"),
    WRITE ("read_and_write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
