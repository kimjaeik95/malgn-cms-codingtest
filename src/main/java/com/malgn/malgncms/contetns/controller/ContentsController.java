package com.malgn.malgncms.contetns.controller;

import com.malgn.malgncms.auth.AuthenticateMember;
import com.malgn.malgncms.contetns.dto.ContentsRequest;
import com.malgn.malgncms.contetns.dto.ContentsResponse;
import com.malgn.malgncms.contetns.service.ContentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
