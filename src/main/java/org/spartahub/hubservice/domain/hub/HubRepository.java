package org.spartahub.hubservice.domain.hub;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, HubId> {
}
