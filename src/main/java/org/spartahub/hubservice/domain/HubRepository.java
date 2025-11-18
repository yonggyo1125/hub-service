package org.spartahub.hubservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, HubId> {
}
