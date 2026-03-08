package com.malgn.malgncms.contetns.service;

import com.malgn.malgncms.auth.AuthenticateMember;
import com.malgn.malgncms.contetns.dto.ContentsRequest;
import com.malgn.malgncms.contetns.dto.ContentsResponse;
import com.malgn.malgncms.contetns.repository.ContentsRepository;
import com.malgn.malgncms.domain.entity.Content;
import com.malgn.malgncms.domain.entity.User;
import com.malgn.malgncms.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : com.malgn.malgncms.contetns.service
 * fileName       : ContentsServicelmpl
 * author         : JAEIK
 * date           : 3/9/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/9/26        JAEIK       최초 생성
 */
@RequiredArgsConstructor
@Service
public class ContentsServiceImpl implements ContentsService{
    private final ContentsRepository contentsRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public ContentsResponse createContent(AuthenticateMember authenticateMember, ContentsRequest contentsRequest) {
        User user = userRepository.findByUsername(authenticateMember.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자 이름을 찾을 수 없습니다."));

        Content content = Content.toEntity(user.getUsername(),contentsRequest);
        contentsRepository.save(content);
        return ContentsResponse.toDto(content);
    }

    @Override
    public List<ContentsResponse> getContents(Pageable pageable) {
        Page<Content> contents = contentsRepository.findAll(pageable);
        if (contents.isEmpty()) {
            throw new IllegalArgumentException("콘텐츠가 없습니다.");
        }
        return contents.getContent().stream()
                .map(ContentsResponse::toDto)
                .collect(Collectors.toList());
    }
}
