package com.example.cms.domain.repository;

import com.example.cms.application.dto.ContentRequestCommand;
import com.example.cms.domain.entity.Contents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentRepository {

    Page<Contents> findAll(Pageable pageable);

    Long createContent(ContentRequestCommand contentRequestCommand);

    Contents save(Contents content);

    Contents findById(Long id);

    void updateContent(Long id, ContentRequestCommand contentRequestCommand);

    void deleteById(Long id);
}
