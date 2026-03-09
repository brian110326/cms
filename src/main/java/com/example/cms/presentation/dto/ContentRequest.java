package com.example.cms.presentation.dto;

import com.example.cms.application.dto.ContentRequestCommand;
import jakarta.validation.constraints.NotBlank;

public record ContentRequest(

        @NotBlank
        String title,

        String description,

        @NotBlank
        String createdBy

) {
        public ContentRequestCommand toCommand() {
                return new ContentRequestCommand(
                        title,
                        description,
                        createdBy
                );
        }
}
