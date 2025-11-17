package org.spartahub.hubservice.infrastructure.security;

import org.spartahub.hubservice.domain.hub.HubRoleCheck;
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
        if (auth != null && auth.getPrincipal() instanceof UserDetails userDetails) {

        }
    }
}
