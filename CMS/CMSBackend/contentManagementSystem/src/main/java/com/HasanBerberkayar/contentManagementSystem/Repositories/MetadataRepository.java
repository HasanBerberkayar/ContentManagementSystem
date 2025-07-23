package com.HasanBerberkayar.contentManagementSystem.Repositories;

import com.HasanBerberkayar.contentManagementSystem.Entities.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Long> {
 //Custom query örneği bak
}
