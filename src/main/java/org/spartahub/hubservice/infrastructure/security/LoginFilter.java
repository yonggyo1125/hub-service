package org.spartahub.hubservice.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.UUID;

/**
 * API 게이트웨이를 통해 넘어온 회원 데이터를 로그인 처리
 *  X-User-Id : 회원 식별자
 *  X-Username : 로그인 아이디
 *  X-User-Roles
 *  X-User-Email
 *  X-User-Name : 회원명
 */
@Component
public class LoginFilter extends GenericFilterBean {

    private static final String HEADER_USER_ID = "X-User-Id";
    private static final String HEADER_USERNAME = "X-Username";
    private static final String HEADER_ROLES = "X-User-Roles";
    private static final String HEADER_EMAIL = "X-User-Email";
    private static final String HEADER_USER_NAME = "X-User-Name";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 로그인 처리
        doLogin((HttpServletRequest)request);

        chain.doFilter(request, response);
    }

    /**
     * 로그인 처리
     *
     * @param request
     */
    private void doLogin(HttpServletRequest request) {
        String userId = request.getHeader(HEADER_USER_ID);
        String username = request.getHeader(HEADER_USERNAME);
        String name = request.getHeader(HEADER_USER_NAME);
        String email = request.getHeader(HEADER_EMAIL);
        String roles = request.getHeader(HEADER_ROLES);

        // userId와 username은 로그인을 위한 필수 항목
        if (!StringUtils.hasText(userId) || !StringUtils.hasText(username)) {
            return;
        }

        UserDetails userDetails = UserDetailsImpl.builder()
                .uuid(UUID.fromString(userId))
                .username(username)
                .email(email)
                .name(name)
                .roles(roles)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // 인증 처리
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
