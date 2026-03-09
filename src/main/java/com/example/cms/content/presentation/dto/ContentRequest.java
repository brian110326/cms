package com.example.cms.content.presentation.dto;

import com.example.cms.content.application.dto.ContentRequestCommand;
import jakarta.validation.constraints.NotBlank;

public record ContentRequest(

        @NotBlank
        String title,

        @NotBlank
        String description

) {
        public ContentRequestCommand toCommand() {
                return new ContentRequestCommand(
                        title,
                        description
                );
        }
}
