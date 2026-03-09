package com.example.cms.presentation.dto;

import com.example.cms.application.dto.ContentResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentResponse {

    private Long id;
    private String title;
    private String description;
    private Long viewCount;

    public static ContentResponse from(ContentResponseDto dto) {
        return ContentResponse.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .viewCount(dto.getViewCount())
                .build();
    }

}
