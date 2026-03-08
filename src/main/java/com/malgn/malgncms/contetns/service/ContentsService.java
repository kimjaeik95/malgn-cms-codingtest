package com.malgn.malgncms.contetns.service;

import com.malgn.malgncms.auth.AuthenticateMember;
import com.malgn.malgncms.contetns.dto.ContentsRequest;
import com.malgn.malgncms.contetns.dto.ContentsResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.malgn.malgncms.contetns.service
 * fileName       : ContentsServcie
 * author         : JAEIK
 * date           : 3/9/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/9/26        JAEIK       최초 생성
 */
public interface ContentsService {
    ContentsResponse createContent(AuthenticateMember AuthenticateMember, ContentsRequest contentsRequest);

    List<ContentsResponse> getContents(Pageable pageable);

    ContentsResponse getContent(Long id);

}
