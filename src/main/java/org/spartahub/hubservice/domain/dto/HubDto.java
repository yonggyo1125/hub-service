package org.spartahub.hubservice.domain.dto;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record HubDto(
    Long id,
    String hubName,
    Long centralHubId,
    String address,
    double latitude,
    double longitude,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) implements Serializable {}
