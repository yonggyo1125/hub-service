package org.spartahub.hubservice.application.hub;

import lombok.RequiredArgsConstructor;
import org.spartahub.hubservice.domain.Hub;
import org.spartahub.hubservice.domain.HubAddressToCoords;
import org.spartahub.hubservice.domain.HubRepository;
import org.spartahub.hubservice.domain.HubRoleCheck;
import org.spartahub.hubservice.domain.dto.HubDto;
import org.spartahub.hubservice.presentation.dto.HubRequest;
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
    public HubDto create(HubRequest request) {

        Hub hub = Hub.builder()
                .hubName(request.hubName())
                .address(request.address())
                .centralHubId(request.centralHubId()) // 중앙 허브 ID
                .hubRoleCheck(roleCheck)
                .addressToCoords(addressToCoords)
                .build();

        repository.save(hub);

        return hub.toDto();
    }
}
