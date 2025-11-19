package org.spartahub.hubservice.domain;

import jakarta.persistence.*;

@Entity
public class HubRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="source")
    private Long startHub;

    @Column(name="target")
    private Long endHub;

    private double cost; // 정방향 통행 비용

    private double reverseCost; // 역방향 통행비용

}
