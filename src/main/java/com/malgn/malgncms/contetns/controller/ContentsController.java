package com.malgn.malgncms.contetns.controller;

import com.malgn.malgncms.auth.AuthenticateMember;
import com.malgn.malgncms.contetns.dto.ContentsRequest;
import com.malgn.malgncms.contetns.dto.ContentsResponse;
import com.malgn.malgncms.contetns.service.ContentsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName    : com.malgn.malgncms.contetns.controller
 * fileName       : ContentsController
 * author         : JAEIK
 * date           : 3/9/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/9/26        JAEIK       최초 생성
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContentsController {
    private final ContentsService contentsService;

    @Operation(description = "콘텐츠 추가")
    @PostMapping("/contents")
    public ResponseEntity<ContentsResponse> createContent(
            @AuthenticationPrincipal AuthenticateMember authenticateMember,
            @Valid @RequestBody ContentsRequest contentsRequest) {
        ContentsResponse contentsResponse = contentsService.createContent(authenticateMember, contentsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(contentsResponse);
    }

    @Operation(description = "콘텐츠 전체 조회 (페이징)")
    @GetMapping("/contents")
    public ResponseEntity<List<ContentsResponse>> getContests(
            @ParameterObject
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable) {

        List<ContentsResponse> contentsResponseList = contentsService.getContents(pageable);
        return ResponseEntity.ok().body(contentsResponseList);
    }

    @Operation(description = "콘텐츠 상세조회")
    @GetMapping("/contents/{id}")
    public ResponseEntity<ContentsResponse> getContent(@PathVariable("id") Long id) {
        ContentsResponse contentsResponse = contentsService.getContent(id);
        return ResponseEntity.ok().body(contentsResponse);
    }

    @Operation(description = "콘텐츠 수정")
    @PutMapping("/contents/{id}")
    public ResponseEntity<ContentsResponse> updateContent(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal AuthenticateMember authenticateMember,
            @RequestBody ContentsRequest contentsRequest) {

        ContentsResponse contentsResponse = contentsService.updateContent(id, authenticateMember, contentsRequest);

        return ResponseEntity.ok(contentsResponse);
    }

    @Operation(description = "콘텐츠 삭제")
    @DeleteMapping("/contents/{id}")
    public ResponseEntity<Void> DeleteContent(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal AuthenticateMember authenticateMember
           ) {

        contentsService.deleteContent(id, authenticateMember);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
