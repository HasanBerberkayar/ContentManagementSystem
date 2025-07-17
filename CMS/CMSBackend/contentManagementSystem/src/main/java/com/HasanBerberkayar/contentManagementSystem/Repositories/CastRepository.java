package com.HasanBerberkayar.contentManagementSystem.Repositories;

import com.HasanBerberkayar.contentManagementSystem.Entities.Casts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastRepository extends JpaRepository<Casts, Long> {

}
