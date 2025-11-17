package org.spartahub.hubservice.domain.hub;

import org.spartahub.hubservice.domain.hub.dto.HubDto;

import java.util.Collection;
import java.util.List;

public interface HubDetailsRepository {
    HubDto findById(HubId id);

    List<HubDto> findAll(Collection<HubId> ids);
}
