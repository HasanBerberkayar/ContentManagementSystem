package com.HasanBerberkayar.contentManagementSystem.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private Metadata metadata;

    @ManyToOne
    @JoinColumn(name = "director")
    private Casts director;

    @ManyToMany
    @JoinTable(
            name = "content_cast",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "cast_id")
    )
    private List<Casts> actors;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Content() {
    }

    public Content(Metadata metadata, Casts director, List<Casts> actors) {
        this.metadata = metadata;
        this.director = director;
        this.actors = actors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Casts getDirector() {
        return director;
    }

    public void setDirector(Casts director) {
        this.director = director;
    }

    public List<Casts> getActors() {
        return actors;
    }

    public void setActors(List<Casts> actors) {
        this.actors = actors;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", metadata=" + metadata +
                ", director=" + director +
                ", actors=" + actors +
                ", createdAt=" + createdAt +
                '}';
    }
}
