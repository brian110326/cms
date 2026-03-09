package com.example.cms.content.domain.repository;

import com.example.cms.content.application.dto.ContentRequestCommand;
import com.example.cms.content.domain.entity.Contents;
import com.example.cms.user.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentRepository {

    Page<Contents> findAll(Pageable pageable);

    Long createContent(ContentRequestCommand contentRequestCommand, UserDetailsImpl userDetails);

    Contents save(Contents content);

    Contents findById(Long id);

    void updateContent(Long id, ContentRequestCommand contentRequestCommand, UserDetailsImpl userDetails);

    void deleteById(Long id);
}
