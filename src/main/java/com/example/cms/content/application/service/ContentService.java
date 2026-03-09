package com.example.cms.content.application.service;

import com.example.cms.content.application.dto.ContentRequestCommand;
import com.example.cms.content.application.dto.ContentResponseDto;
import com.example.cms.content.domain.entity.Contents;
import com.example.cms.content.domain.repository.ContentRepository;
import com.example.cms.user.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

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
    public Long createContent(ContentRequestCommand request, String username){
        Contents content = Contents.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .createdBy(username)
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
    public void updateContent(Long id, ContentRequestCommand request, UserDetailsImpl userDetails) throws AccessDeniedException {

        Contents content = contentRepository.findById(id);

        validateUser(content, userDetails);

        content.updateContent(
                request.getTitle(),
                request.getDescription(),
                userDetails.getUsername()
        );
    }

    private void validateUser(Contents content, UserDetailsImpl userDetails) throws AccessDeniedException {
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = content.getCreatedBy().equals(userDetails.getUsername());

        if(!isAdmin && !isOwner){
            throw new AccessDeniedException("권한이 없습니댜.");
        }
    }

    // 콘텐츠 삭제
    public void deleteContent(Long id, UserDetailsImpl userDetails) throws AccessDeniedException {
        Contents content = contentRepository.findById(id);
        validateUser(content, userDetails);
        contentRepository.deleteById(id);
    }

}
