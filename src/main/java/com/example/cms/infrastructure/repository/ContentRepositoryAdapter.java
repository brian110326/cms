package com.example.cms.infrastructure.repository;

import com.example.cms.application.dto.ContentRequestCommand;
import com.example.cms.domain.entity.Contents;
import com.example.cms.domain.repository.ContentRepository;
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
    public Long createContent(ContentRequestCommand contentRequestCommand) {
        Contents contents = Contents.builder()
                .title(contentRequestCommand.getTitle())
                .description(contentRequestCommand.getDescription())
                .viewCount(0L)
                .createdBy(contentRequestCommand.getCreatedBy())
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
    public void updateContent(Long id, ContentRequestCommand contentRequestCommand) {
        Contents contents = jpaContentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("해당 id의 content는 존재하지 않습니다."));

        contents.updateContent(contentRequestCommand.getTitle(), contentRequestCommand.getDescription(),
                contentRequestCommand.getCreatedBy());
    }

    @Override
    public void deleteById(Long id) {
        jpaContentRepository.deleteById(id);
    }
}
