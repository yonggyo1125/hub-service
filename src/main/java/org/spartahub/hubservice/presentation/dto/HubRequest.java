package org.spartahub.hubservice.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record HubRequest(
        @NotBlank
        String hubName,
        @NotNull
        UUID centralHubId,
        @NotBlank
        String address
) {}
