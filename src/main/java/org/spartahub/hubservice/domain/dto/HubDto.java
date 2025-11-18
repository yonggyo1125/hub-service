package org.spartahub.hubservice.domain.dto;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record HubDto(
    UUID id,
    String hubName,
    UUID centralHubId,
    String address,
    double latitude,
    double longitude,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) implements Serializable {}
