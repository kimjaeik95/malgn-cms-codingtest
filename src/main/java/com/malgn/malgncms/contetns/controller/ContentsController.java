package com.malgn.malgncms.contetns.controller;

import com.malgn.malgncms.auth.AuthenticateMember;
import com.malgn.malgncms.contetns.dto.ContentsRequest;
import com.malgn.malgncms.contetns.dto.ContentsResponse;
import com.malgn.malgncms.contetns.service.ContentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/contents")
    public ResponseEntity<ContentsResponse> createContent(
            @AuthenticationPrincipal AuthenticateMember authenticateMember,
            @Valid @RequestBody ContentsRequest contentsRequest) {
        ContentsResponse contentsResponse = contentsService.createContent(authenticateMember, contentsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(contentsResponse);
    }

    @GetMapping("/contents")
    public ResponseEntity<List<ContentsResponse>> getContests(
            @ParameterObject
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable) {

        List<ContentsResponse> contentsResponseList = contentsService.getContents(pageable);
        return ResponseEntity.ok().body(contentsResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentsResponse> getContent(@PathVariable("id") Long id) {
        ContentsResponse contentsResponse = contentsService.getContent(id);
        return ResponseEntity.ok().body(contentsResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentsResponse> updateContent(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal AuthenticateMember authenticateMember,
            @RequestBody ContentsRequest contentsRequest) {

        ContentsResponse contentsResponse = contentsService.updateContent(id, authenticateMember, contentsRequest);

        return ResponseEntity.ok(contentsResponse);
    }
}
