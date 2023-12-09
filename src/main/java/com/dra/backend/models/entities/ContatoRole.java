package com.dra.backend.models.entities;

public enum ContatoRole {
    USER("USER"), ADMIN("ADMIN");

    private final String role;

    ContatoRole(String value) {
        this.role = value;
    }

    public String getRole() {
        return role;
    }
}
