package com.HasanBerberkayar.contentManagementSystem.Repositories;

import com.HasanBerberkayar.contentManagementSystem.Entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

}
