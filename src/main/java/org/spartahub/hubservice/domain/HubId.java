package org.spartahub.hubservice.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@ToString
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubId {
    private UUID id;

    protected HubId(UUID id) {
        this.id = id;
    }

    public static HubId of(UUID id) {
        id = Objects.requireNonNullElse(id, UUID.randomUUID());
        return new HubId(id);
    }

    public static HubId of() {
        return HubId.of(null);
    }
}
