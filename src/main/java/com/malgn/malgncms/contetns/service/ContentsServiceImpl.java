package com.malgn.malgncms.contetns.service;

import com.malgn.malgncms.auth.AuthenticateMember;
import com.malgn.malgncms.auth.Role;
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
import java.util.Optional;
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
public class ContentsServiceImpl implements ContentsService {
    private final ContentsRepository contentsRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ContentsResponse createContent(AuthenticateMember authenticateMember, ContentsRequest contentsRequest) {
        User user = userRepository.findByUsername(authenticateMember.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자 이름을 찾을 수 없습니다."));

        Content content = Content.toEntity(user.getUsername(), contentsRequest);
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

    @Override
    @Transactional
    public ContentsResponse getContent(Long id) {
        Content content = contentsRepository.findByIdWithLock(id)
                .orElseThrow(() -> new IllegalArgumentException("콘텐츠가 없습니다."));

        content.viewCountPlus();

        return ContentsResponse.toDto(content);
    }

    @Override
    @Transactional
    public ContentsResponse updateContent(Long contentId, AuthenticateMember authenticateMember, ContentsRequest contentsRequest) {
        Content content = contentsRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("콘텐츠가 없습니다."));

        checkUpdateDelete(content, authenticateMember, "수정");

        content.updateContent(contentsRequest, authenticateMember.getUsername());

        return ContentsResponse.toDto(content);
    }
    @Override
    @Transactional
    public void deleteContent(Long contentId, AuthenticateMember authenticateMember) {
        Content content = contentsRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("콘텐츠가 없습니다."));

        checkUpdateDelete(content, authenticateMember, "삭제");

        contentsRepository.delete(content);
    }


    // 공통 메서드 분리
    private void checkUpdateDelete( Content content, AuthenticateMember authenticateMember, String action) {
        boolean isAdmin = Role.ADMIN.name().equals(authenticateMember.getRole());
        boolean isMine = content.getCreateBy().equals(authenticateMember.getUsername());

        boolean possibleUpdateDelete = isMine || isAdmin;

        if (!possibleUpdateDelete) {
            throw new IllegalArgumentException(action + " 권한이 없습니다.");
        }
    }
}
