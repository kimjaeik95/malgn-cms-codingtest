package com.malgn.malgncms.auth.jwt;

import com.malgn.malgncms.auth.AuthenticateMember;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.malgn.malgncms.jwt
 * fileName       : JwtFilter
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtParser jwtParser;
    private final JwtValidator jwtValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null && jwtValidator.validateToken(token)) {
            Map<String, Object> claims = jwtParser.parseClaims(token);
            Long id = Long.valueOf(claims.get("id").toString());
            String username = claims.get("username").toString();
            String role = claims.get("role").toString();

            AuthenticateMember authenticateMember = new AuthenticateMember(id, username, role);

            Authentication auth = getAuthentication(authenticateMember);

            SecurityContextHolder.getContext().setAuthentication(auth);
        }


        filterChain.doFilter(request, response);
    }

    // 헤더에서 Authorization 토큰가져오고 bearer 제거하고 반환
    private String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(JwtProperties.ACCESS_TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(JwtProperties.ACCESS_TOKEN_PREFIX)) {
            return bearerToken.substring(JwtProperties.ACCESS_TOKEN_PREFIX.length());
        }
        return null;
    }

    // 시큐리티가 인식할 수 있는 객체로 변환
    private Authentication getAuthentication(AuthenticateMember member) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(member.getRole()));
        return new UsernamePasswordAuthenticationToken(member, null, authorities);
    }
}

