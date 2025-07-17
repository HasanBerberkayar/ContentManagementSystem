package com.HasanBerberkayar.contentManagementSystem.Services;

import com.HasanBerberkayar.contentManagementSystem.DTO.CastRequest;
import com.HasanBerberkayar.contentManagementSystem.Entities.Casts;

import java.util.List;

public interface CastService {

    List<Casts> getCasts();
    Casts addCast(CastRequest request);
    public void deleteCast(long id);
}
