package com.example.cms.content.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentResponseDto {

    private Long id;
    private String title;
    private String description;
    private Long viewCount;

}
