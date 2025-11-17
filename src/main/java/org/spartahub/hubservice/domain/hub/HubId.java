package org.spartahub.hubservice.domain.hub;

import jakarta.persistence.Embeddable;
import lombok.*;

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
        return new HubId(id);
    }

    public static HubId of() {
        return HubId.of(UUID.randomUUID());
    }
}
