package com.example.cms.infrastructure.repository;

import com.example.cms.domain.entity.Contents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaContentRepository extends JpaRepository<Contents, Long> {

    Page<Contents> findAll(Pageable pageable);
}
