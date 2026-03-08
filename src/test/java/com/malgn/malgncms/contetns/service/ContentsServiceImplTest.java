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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
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

    @Test
    @DisplayName("콘텐츠 본인만 수정 성공 테스트")
    void updateContentTest() {
        // Given
        Long contentId = 1L;
        String username = "jaeik";
        String role = String.valueOf(Role.USER);

        // 게시글 생성, 유저객체 담기
        ContentsRequest updateRequest = new ContentsRequest("수정 제목", "수정 내용");
        AuthenticateMember authMember = new AuthenticateMember(1L, username, role);

        // 데이터준비
        Content existingContent = Content.builder()
                .title("원래 제목")
                .description("원래 내용")
                .createBy(username)
                .build();

        given(contentsRepository.findById(contentId)).willReturn(Optional.of(existingContent));

        // When
        ContentsResponse result = contentsService.updateContent(contentId, authMember, updateRequest);

        // Then
        // 제목과 내용이 성공적으로 수정되었는지 확인
        assertThat(result.getTitle()).isEqualTo("수정 제목");
        assertThat(result.getDescription()).isEqualTo("수정 내용");

        // 콘텐츠 작성자랑 , 로그인 된 작성자랑 같은지
        assertThat(existingContent.getCreateBy()).isEqualTo(authMember.getUsername());

        // 수정자 정보가 현재 유저로 기록되었는지 확인
        assertThat(existingContent.getLastModifiedBy()).isEqualTo(username);

        //  리포지토리 조회가 정상적으로 수행되었는지 확인
        verify(contentsRepository, times(1)).findById(contentId);
    }

    @Test
    @DisplayName("관리자는 작성자가 아니어도 모든 콘텐츠를 수정할 수 있다 성공테스트")
    void updateContentByAdmin() {
        // Given
        Long contentId = 1L;
        String adminUsername = "adminJack";
        String role = String.valueOf(Role.ADMIN);

        // 1. 관리자 권한을 가진 인증 객체 생성
        AuthenticateMember adminMember = new AuthenticateMember(1L, adminUsername, role);
        ContentsRequest updateRequest = new ContentsRequest("관리자가 수정한 제목", "관리자가 수정한 내용");

        // 2. 다른 사람이 작성한 기존 게시글 데이터 준비
        Content existingContent = Content.builder()
                .title("원래 제목")
                .description("원래 내용")
                .createBy("다른작성자")
                .build();

        given(contentsRepository.findById(contentId)).willReturn(Optional.of(existingContent));

        // When
        ContentsResponse result = contentsService.updateContent(contentId, adminMember, updateRequest);

        // Then
        // 작성자가 아님에도 isAdmin 조건에 의해 수정이 성공해야 함
        assertThat(result.getTitle()).isEqualTo("관리자가 수정한 제목");
        assertThat(result.getDescription()).isEqualTo("관리자가 수정한 내용");

        // 엔티티의 최종 수정자가 관리자 이름으로 남았는지 확인
        assertThat(existingContent.getLastModifiedBy()).isEqualTo(adminUsername);

        // 리포지토리 조회 호출 확인
        verify(contentsRepository, times(1)).findById(contentId);
    }

    @Test
    @DisplayName("User 권한은 작성자가 아니어도 모든 콘텐츠를 수정할 수 없다. 오류 테스트 ")
    void updateContentByUser() {
        // Given
        Long contentId = 1L;
        String otherUsername = "userJack";
        String role = String.valueOf(Role.USER);

        // 1. 관리자 권한을 가진 인증 객체 생성
        AuthenticateMember userMember = new AuthenticateMember(1L, otherUsername, role);
        ContentsRequest updateRequest = new ContentsRequest("수정 시도 제목", "수정 시도 내용");

        // 2. 다른 사람이 작성한 기존 게시글 데이터 준비
        Content existingContent = Content.builder()
                .title("원래 제목")
                .description("원래 내용")
                .createBy("다른작성자")
                .build();

        given(contentsRepository.findById(contentId)).willReturn(Optional.of(existingContent));


        // User 권한 +  다른사람 콘텐츠 수정시 실패 해야 함
        assertThatThrownBy(() -> contentsService.updateContent(contentId, userMember, updateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수정 권한이 없습니다.");
    }

    @Test
    @DisplayName("관리자는 작성자가 아니어도 모든 콘텐츠를 수정할 수 있다 성공테스트")
    void deleteContent_AdminSuccess() {
        // Given
        Long contentId = 1L;
        AuthenticateMember admin = new AuthenticateMember(1L, "adminUser", Role.ADMIN.name());

        Content existingContent = Content.builder()
                .title("원래 제목")
                .description("원래 내용")
                .createBy("다른작성자")
                .build();

        given(contentsRepository.findById(contentId)).willReturn(Optional.of(existingContent));

        // When
        contentsService.deleteContent(contentId, admin);

        // Then
        // 실제로 리포지토리의 delete 메서드가 호출되었는지 확인
        verify(contentsRepository, times(1)).delete(existingContent);
    }

    @Test
    @DisplayName("User 권한은 작성자가 아니어도 모든 콘텐츠를 삭제 할 수 없다. 오류 테스트 ")
    void deleteContent_UserFail() {
        // Given
        Long contentId = 1L;
        AuthenticateMember otherUser = new AuthenticateMember(2L, "otherUser", Role.USER.name());

        Content existingContent = Content.builder()
                .title("원래 제목")
                .description("원래 내용")
                .createBy("다른작성자")
                .build();

        given(contentsRepository.findById(contentId)).willReturn(Optional.of(existingContent));

        // When & Then
        assertThatThrownBy(() -> contentsService.deleteContent(contentId, otherUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("삭제 권한이 없습니다.");

        // 예외가 터졌으므로 delete 메서드는 호출되면 안 됨
        verify(contentsRepository, never()).delete(any(Content.class));
    }
}
