package com.HasanBerberkayar.contentManagementSystem.DTO;

import com.HasanBerberkayar.contentManagementSystem.Configs.Roles;

public class CastRequest {
    private String name;
    private String poster;
    private Roles role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
