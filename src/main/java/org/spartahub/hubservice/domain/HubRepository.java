package org.spartahub.hubservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface HubRepository extends JpaRepository<Hub, Long>, QuerydslPredicateExecutor<Hub> {
}
