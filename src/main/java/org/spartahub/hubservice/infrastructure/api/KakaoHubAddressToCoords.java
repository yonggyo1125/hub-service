package org.spartahub.hubservice.infrastructure.api;

import org.spartahub.hubservice.domain.hub.HubAddressToCoords;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KakaoHubAddressToCoords implements HubAddressToCoords {
    @Override
    public List<Double> convert(String address) {
        return List.of();
    }
}
