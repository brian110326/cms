package com.example.cms.content.presentation.dto;

import com.example.cms.content.application.dto.ContentResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "ContentRequest", description = "Content 작성 요청 DTO")
public class ContentResponse {

    @Schema(description = "컨텐츠 id", example = "1")
    private Long id;

    @Schema(description = "컨텐츠 제목", example = "Title1")
    private String title;

    @Schema(description = "컨텐츠 내용", example = "Des1")
    private String description;

    @Schema(description = "컨텐츠 조회 수", example = "25")
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
