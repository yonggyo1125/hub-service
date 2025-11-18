package org.spartahub.hubservice.domain;

import org.spartahub.hubservice.domain.dto.HubDto;

import java.util.Collection;
import java.util.List;

public interface HubDetailsRepository {
    HubDto findById(HubId id);

    List<HubDto> findAll(Collection<HubId> ids);
}
