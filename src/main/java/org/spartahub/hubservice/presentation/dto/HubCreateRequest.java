package org.spartahub.hubservice.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record HubCreateRequest(
        @NotBlank
        String hubName,
        @NotBlank
        String address
) {}
