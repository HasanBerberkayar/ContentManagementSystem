package com.HasanBerberkayar.contentManagementSystem.Entities;

import com.HasanBerberkayar.contentManagementSystem.Configs.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
public class Casts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String poster;
    private Roles role;

    public Casts() {
    }

    public Casts(String name, String poster, Roles role) {
        this.name = name;
        this.poster = poster;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Casts{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }

}
