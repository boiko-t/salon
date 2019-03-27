package com.boiko.taisa.salon.domain.entity;

public enum UserRole {
    ADMIN("admin"), MATER("master");
    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
