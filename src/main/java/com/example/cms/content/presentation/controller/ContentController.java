package com.example.cms.content.presentation.controller;

import com.example.cms.content.application.dto.ContentResponseDto;
import com.example.cms.content.application.service.ContentService;
import com.example.cms.content.presentation.dto.ApiResponse;
import com.example.cms.content.presentation.dto.ContentRequest;
import com.example.cms.content.presentation.dto.ContentResponse;
import com.example.cms.user.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
@Tag(name = "Content-Controller", description = "컨텐츠 생성, 조회, 수정, 삭제 API 모음")
public class ContentController {

    private final ContentService contentService;

    // -------------------- 컨텐츠 생성 --------------------
    @Operation(summary = "컨텐츠 생성 API", description = "컨텐츠 제목과 내용을 요청해 생성합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "컨텐츠 생성 성공",
                    content = @Content(
                            schema = @Schema(implementation = Long.class),
                            examples = @ExampleObject(value = "{ \"id\": 1 }")
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createContent(
            @Parameter(description = "컨텐츠 생성 요청 DTO") @Valid @RequestBody ContentRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long id = contentService.createContent(request.toCommand(), userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(id, "콘텐츠가 생성되었습니다."));
    }

    // -------------------- 컨텐츠 목록 조회 --------------------
    @Operation(summary = "컨텐츠 목록 조회 API", description = "컨텐츠 전체 목록을 페이징 적용 상태로 조회합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "컨텐츠 목록 조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = ContentResponse.class),
                            examples = @ExampleObject(value = "{ \"content\": [{ \"id\": 1, \"title\": \"첫 번째 글\", \"description\": \"내용\", \"createdBy\": \"admin\" }] }")
                    )
            )
    })
    @GetMapping
    public ApiResponse<Page<ContentResponse>> getContents(
            @Parameter(description = "페이지네이션 정보") @ParameterObject
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ContentResponseDto> contents = contentService.getContents(pageable);
        Page<ContentResponse> response = contents.map(ContentResponse::from);
        return ApiResponse.success(response);
    }

    // -------------------- 컨텐츠 상세 조회 --------------------
    @Operation(summary = "특정 컨텐츠 조회 API", description = "컨텐츠 id값을 통해 단건 조회합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "컨텐츠 조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = ContentResponse.class),
                            examples = @ExampleObject(value = "{ \"id\": 1, \"title\": \"첫 번째 글\", \"description\": \"내용\", \"createdBy\": \"admin\" }")
                    )
            )
    })
    @GetMapping("/{id}")
    public ApiResponse<ContentResponse> getContent(
            @Parameter(description = "조회할 컨텐츠 id") @PathVariable Long id
    ) {
        ContentResponseDto content = contentService.getContent(id);
        return ApiResponse.success(ContentResponse.from(content));
    }

    // -------------------- 컨텐츠 수정 --------------------
    @Operation(summary = "컨텐츠 수정 API", description = "ADMIN 권한자 또는 작성자만 수정 가능")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "컨텐츠 수정 성공",
                    content = @Content(schema = @Schema(implementation = Void.class))
            )
    })
    @PutMapping("/{id}")
    public ApiResponse<Void> updateContent(
            @Parameter(description = "수정할 컨텐츠 id") @PathVariable Long id,
            @Parameter(description = "컨텐츠 수정 요청 DTO") @Valid @RequestBody ContentRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        contentService.updateContent(id, request.toCommand(), userDetails);
        return ApiResponse.success("콘텐츠가 수정되었습니다.");
    }

    // -------------------- 컨텐츠 삭제 --------------------
    @Operation(summary = "컨텐츠 삭제 API", description = "ADMIN 권한자 또는 작성자만 삭제 가능")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "컨텐츠 삭제 성공",
                    content = @Content(schema = @Schema(implementation = Void.class))
            )
    })
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteContent(
            @Parameter(description = "삭제할 컨텐츠 id") @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        contentService.deleteContent(id, userDetails);
        return ApiResponse.success("콘텐츠가 삭제되었습니다.");
    }
}