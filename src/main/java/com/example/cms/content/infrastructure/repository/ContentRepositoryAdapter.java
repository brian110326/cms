package com.example.cms.content.infrastructure.repository;

import com.example.cms.content.application.dto.ContentRequestCommand;
import com.example.cms.content.domain.entity.Contents;
import com.example.cms.content.domain.repository.ContentRepository;
import com.example.cms.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ContentRepositoryAdapter implements ContentRepository {

    private final JpaContentRepository jpaContentRepository;

    @Override
    public Page<Contents> findAll(Pageable pageable) {
        return jpaContentRepository.findAll(pageable);
    }

    @Override
    public Long createContent(ContentRequestCommand contentRequestCommand, UserDetailsImpl userDetails) {
        Contents contents = Contents.builder()
                .title(contentRequestCommand.getTitle())
                .description(contentRequestCommand.getDescription())
                .viewCount(0L)
                .createdBy(userDetails.getUsername())
                .build();

        return jpaContentRepository.save(contents).getId();
    }

    @Override
    public Contents save(Contents content) {
        return jpaContentRepository.save(content);
    }

    @Override
    public Contents findById(Long id) {
        return jpaContentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("해당 id의 content는 존재하지 않습니다."));
    }

    @Override
    public void updateContent(Long id, ContentRequestCommand contentRequestCommand, UserDetailsImpl userDetails) {
        Contents contents = jpaContentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("해당 id의 content는 존재하지 않습니다."));

        contents.updateContent(contentRequestCommand.getTitle(), contentRequestCommand.getDescription(),
                userDetails.getUsername());
    }

    @Override
    public void deleteById(Long id) {
        jpaContentRepository.deleteById(id);
    }
}
