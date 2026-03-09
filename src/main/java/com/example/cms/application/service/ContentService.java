package com.example.cms.application.service;

import com.example.cms.application.dto.ContentRequestCommand;
import com.example.cms.application.dto.ContentResponseDto;
import com.example.cms.domain.entity.Contents;
import com.example.cms.domain.repository.ContentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    // 콘텐츠 목록 조회 (페이징)
    public Page<ContentResponseDto> getContents(Pageable pageable){
        return contentRepository.findAll(pageable)
                .map(content -> ContentResponseDto.builder()
                        .id(content.getId())
                        .title(content.getTitle())
                        .description(content.getDescription())
                        .viewCount(content.getViewCount())
                        .build());
    }

    // 콘텐츠 추가
    @Transactional
    public Long createContent(ContentRequestCommand request){
        Contents content = Contents.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .createdBy(request.getCreatedBy())
                .viewCount(0L)
                .build();

        return contentRepository.save(content).getId();
    }

    // 콘텐츠 상세 조회
    public ContentResponseDto getContent(Long id) {

        Contents content = contentRepository.findById(id);

        return ContentResponseDto.builder()
                .id(content.getId())
                .title(content.getTitle())
                .description(content.getDescription())
                .viewCount(content.getViewCount())
                .build();
    }

    // 콘텐츠 수정
    @Transactional
    public void updateContent(Long id, ContentRequestCommand request) {

        Contents content = contentRepository.findById(id);

        content.updateContent(
                request.getTitle(),
                request.getDescription(),
                request.getCreatedBy()
        );
    }

    // 콘텐츠 삭제
    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }

}
