package org.spartahub.hubservice.domain;

import jakarta.persistence.*;

@Entity
public class HubRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name="id", column = @Column(name="source"))
    )
    private HubId startHub;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name="id", column = @Column(name="target"))
    )
    private HubId endHub;

    @Column(columnDefinition = "cost DOUBLE PRECISION")
    public Double cost; // 정방향 통행 비용

    @Column(columnDefinition = "reverse_cost DOUBLE PRECISION")
    public Double reverseCost; // 역방향 통행비용


}
