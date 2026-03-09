package com.example.cms.presentation.controller;

import com.example.cms.application.dto.ContentResponseDto;
import com.example.cms.application.service.ContentService;
import com.example.cms.presentation.dto.ApiResponse;
import com.example.cms.presentation.dto.ContentRequest;
import com.example.cms.presentation.dto.ContentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentController {

    private final ContentService contentService;

    // 콘텐츠 추가
    @PostMapping
    public ApiResponse<Long> createContent(
            @Valid @RequestBody ContentRequest request
    ) {

        Long id = contentService.createContent(request.toCommand());

        return ApiResponse.success(id, "콘텐츠가 생성되었습니다.");
    }

    // 콘텐츠 목록 조회 (페이징)
    @GetMapping
    public ApiResponse<Page<ContentResponse>> getContents(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {

        Page<ContentResponseDto> contents = contentService.getContents(pageable);

        Page<ContentResponse> response = contents.map(ContentResponse::from);

        return ApiResponse.success(response);
    }

    // 콘텐츠 상세 조회
    @GetMapping("/{id}")
    public ApiResponse<ContentResponse> getContent(
            @PathVariable Long id
    ) {

        ContentResponseDto content = contentService.getContent(id);

        return ApiResponse.success(ContentResponse.from(content));
    }

    // 콘텐츠 수정
    @PutMapping("/{id}")
    public ApiResponse<Void> updateContent(
            @PathVariable Long id,
            @Valid @RequestBody ContentRequest request
    ) {

        contentService.updateContent(id, request.toCommand());

        return ApiResponse.success("콘텐츠가 수정되었습니다.");
    }

    // 콘텐츠 삭제 (Soft Delete)
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteContent(
            @PathVariable Long id
    ) {

        contentService.deleteContent(id);

        return ApiResponse.success("콘텐츠가 삭제되었습니다.");
    }
}
