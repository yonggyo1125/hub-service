package org.spartahub.hubservice.application.hub;

import lombok.RequiredArgsConstructor;
import org.spartahub.hubservice.domain.Hub;
import org.spartahub.hubservice.domain.HubRepository;
import org.spartahub.hubservice.infrastructure.persistence.exception.HubNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubDeleteService {
    private final HubRepository repository;

    @Transactional
    @PreAuthorize("hasRole('MASTER')")
    @CacheEvict(cacheNames = "hubItems", allEntries = true)
    public void delete(String username, Long hubId) {
        Hub hub = repository.findById(hubId).orElseThrow(HubNotFoundException::new);
        hub.delete(username);
    }
}
