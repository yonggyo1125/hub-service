package org.spartahub.hubservice.application.hub;

import lombok.RequiredArgsConstructor;
import org.spartahub.hubservice.domain.hub.Hub;
import org.spartahub.hubservice.domain.hub.HubId;
import org.spartahub.hubservice.domain.hub.HubRepository;
import org.spartahub.hubservice.infrastructure.persistence.exception.HubNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubDeleteService {
    private final HubRepository repository;

    @Transactional
    @PreAuthorize("hasRole('MASTER')")
    public void delete(String username, UUID hubId) {
        Hub hub = repository.findById(HubId.of(hubId)).orElseThrow(HubNotFoundException::new);
        hub.delete(username);
    }
}
