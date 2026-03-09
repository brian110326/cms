package com.example.cms.content.presentation.dto;

import com.example.cms.content.application.dto.ContentRequestCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "ContentRequest", description = "Content 작성 요청 DTO")
public record ContentRequest(

        @NotBlank
        @Schema(description = "컨텐츠 제목", example = "Title1")
        String title,

        @NotBlank
        @Schema(description = "컨텐츠 내용", example = "Des1")
        String description

) {
        public ContentRequestCommand toCommand() {
                return new ContentRequestCommand(
                        title,
                        description
                );
        }
}
