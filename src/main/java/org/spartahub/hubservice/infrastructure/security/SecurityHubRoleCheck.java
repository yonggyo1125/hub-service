package org.spartahub.hubservice.infrastructure.security;

import org.spartahub.hubservice.domain.HubRoleCheck;
import org.spartahub.hubservice.domain.exception.UnAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 마스터 권한 체크
 *
 */
@Component
public class SecurityHubRoleCheck implements HubRoleCheck {

    @Override
    public void masterCheck() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isPermitted = false;
        if (auth != null && auth.getPrincipal() instanceof UserDetails userDetails) {
            isPermitted = userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_MASTER"));
        }

        if (!isPermitted) {
            throw new UnAuthorizedException();
        }
    }
}
