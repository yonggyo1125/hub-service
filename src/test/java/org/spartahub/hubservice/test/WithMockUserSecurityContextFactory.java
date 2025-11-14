package org.spartahub.hubservice.test;

import org.spartahub.hubservice.infrastructure.security.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.UUID;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<MockUser> {
    @Override
    public SecurityContext createSecurityContext(MockUser user) {

        UserDetails userDetails = UserDetailsImpl.builder()
                .uuid(UUID.fromString(user.uuid()))
                .username(user.username())
                .name(user.name())
                .email(user.email())
                .roles(String.join(",", user.roles()))
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication); // 로그인 처리

        return context;
    }
}
