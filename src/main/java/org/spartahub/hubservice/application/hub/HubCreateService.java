package org.spartahub.hubservice.application.hub;

import lombok.RequiredArgsConstructor;
import org.spartahub.hubservice.domain.hub.*;
import org.spartahub.hubservice.domain.hub.dto.HubDto;
import org.spartahub.hubservice.presentation.dto.HubCreateRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 허브 등록
 *
 */
@Service
@RequiredArgsConstructor
public class HubCreateService {
    private final HubRepository repository;
    private final HubRoleCheck roleCheck;
    private final HubAddressToCoords addressToCoords;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @CachePut(cacheNames = "hubItem", key="#result.id")
    @CacheEvict(cacheNames = "hubItems", allEntries = true)
    public HubDto create(HubCreateRequest request) {

        Hub hub = Hub.builder()
                .hubName(request.hubName())
                .address(request.address())
                .hubRoleCheck(roleCheck)
                .addressToCoords(addressToCoords)
                .build();

        repository.save(hub);

        return hub.toDto();
    }
}
