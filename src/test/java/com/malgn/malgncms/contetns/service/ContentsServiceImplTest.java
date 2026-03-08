package com.malgn.malgncms.contetns.service;

import com.malgn.malgncms.auth.AuthenticateMember;
import com.malgn.malgncms.auth.Role;
import com.malgn.malgncms.contetns.dto.ContentsRequest;
import com.malgn.malgncms.contetns.dto.ContentsResponse;
import com.malgn.malgncms.contetns.repository.ContentsRepository;
import com.malgn.malgncms.domain.entity.Content;
import com.malgn.malgncms.domain.entity.User;
import com.malgn.malgncms.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * packageName    : com.malgn.malgncms.contetns.service
 * fileName       : ContentsServiceImplTest
 * author         : JAEIK
 * date           : 3/9/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/9/26        JAEIK       최초 생성
 */
@ExtendWith(MockitoExtension.class)
class ContentsServiceImplTest {

    @InjectMocks
    private ContentsServiceImpl contentsService; // 테스트 대상

    @Mock
    private ContentsRepository contentsRepository; // 가짜 저장소

    @Mock
    private UserRepository userRepository; // 가짜 유저 저장소

    @Test
    @DisplayName("콘텐츠 생성 성공 테스트")
    void createContentsTest() {
        // 1. Given (상황 설정)
        Long id = 1L;
        String username = "testUser";
        String role = String.valueOf(Role.USER);
        ContentsRequest request = new ContentsRequest("제목", "내용");
        AuthenticateMember auth = new AuthenticateMember(id, username, role); // 가짜 인증 객체
        User user = User.builder().username(username).build();

        // userRepository.findByUsername이 호출되면 user를 반환 할것
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));

        // contentsRepository.save가 호출되면 인자로 받은 객체를 그대로 반환 할것
        given(contentsRepository.save(any(Content.class))).willAnswer(invocation -> invocation.getArgument(0));

        // 2. When (동작)
        ContentsResponse response = contentsService.createContent(auth, request);

        // 3. Then (검증)
        assertThat(response.getTitle()).isEqualTo("제목");
        assertThat(response.getViewCount()).isEqualTo(0L);
        assertThat(response.getCreatedBy()).isEqualTo(username);
        verify(contentsRepository, times(1)).save(any(Content.class));
    }

    @Test
    @DisplayName("유저가 없으면 에러 발생")
    void contentsTest() {
        // Given
        Long id = 1L;
        String username = "testUser";
        String role = String.valueOf(Role.USER);
        AuthenticateMember auth = new AuthenticateMember(id, username, role);
        given(userRepository.findByUsername(username)).willReturn(Optional.empty()); // 비어있음으로 반환

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            contentsService.createContent(auth, new ContentsRequest("제목", "내용"));
        });
    }

    @Test
    @DisplayName("콘텐츠 목록 페이징 조회 성공")
    void getContentsPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Content content1 = new Content("제목", "내용1", 0L); // 실제 엔티티 구조에 맞게 생성
        Content content2 = new Content("제목", "내용1", 0L);

        Page<Content> mockPage = new PageImpl<>(List.of(content1, content2));

        given(contentsRepository.findAll(pageable)).willReturn(mockPage);

        // When
        List<ContentsResponse> result = contentsService.getContents(pageable);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("제목");
        verify(contentsRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("콘텐츠 상세 조회 성공")
    void getContent() {
        // Given
        Long contentId = 1L;
        Content content = new Content("제목", "내용", 0L);

        given(contentsRepository.findById(contentId)).willReturn(Optional.of(content));

        // When
        ContentsResponse result = contentsService.getContent(contentId);

        // Then
        assertThat(result.getTitle()).isEqualTo("제목");
        assertThat(result.getDescription()).isEqualTo("내용");
        verify(contentsRepository, times(1)).findById(contentId);
    }
}
