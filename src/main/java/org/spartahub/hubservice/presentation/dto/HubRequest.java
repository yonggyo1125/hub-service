package org.spartahub.hubservice.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record HubRequest(
        @NotBlank
        String hubName,
        Long centralHubId, // 값이 없다면 중앙 허브
        @NotBlank
        String address
) {}
