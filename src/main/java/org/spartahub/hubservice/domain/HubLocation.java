package org.spartahub.hubservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubLocation {
    @Column(length=150, nullable = false)
    private String address; // 주소
    private double latitude; // 위도
    private double longitude; // 경도

    @Builder
    protected HubLocation(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
