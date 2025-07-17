package com.HasanBerberkayar.contentManagementSystem.DTO;

import com.HasanBerberkayar.contentManagementSystem.Entities.Metadata;

import java.util.List;


public class ContentRequest {
    private Metadata metadata;
    private long directorId;
    private List<Long> actorIds;

    public Metadata getMetadata() {
        return metadata;
    }

    public long getDirectorId() {
        return directorId;
    }

    public List<Long> getActorIds() {
        return actorIds;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public void setDirectorId(long directorId) {
        this.directorId = directorId;
    }

    public void setActorIds(List<Long> actorIds) {
        this.actorIds = actorIds;
    }
}
