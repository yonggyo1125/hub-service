package org.spartahub.hubservice.domain.hub;

import jakarta.persistence.*;
import lombok.*;
import org.spartahub.hubservice.infrastructure.persistence.BaseUserEntity;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 1. 허브 정보 변경은 마스터 관리자만 가능
 * 2. 허브 주소 등록/수정 시 위도,경도를 업데이트 한다.
 *
 */
@Getter
@ToString
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub extends BaseUserEntity {
    @EmbeddedId
    private HubId id;
    private String hubName;

    @Embedded
    private HubLocation location;

    @Builder
    public Hub(HubId id, String hubName, String address, HubAddressToCoords addressToCoords, HubRoleCheck hubRoleCheck) {
        hubRoleCheck.masterCheck(); // 허브 등록 수정은 마스터 권한으로 한정

        this.id = id;
        this.hubName = hubName;
        setLocation(address, addressToCoords, hubRoleCheck); // 주소 -> 좌표 변환
    }


    // 허브 주소를 위도, 경도 좌표로 설정
    private void setLocation(String address, HubAddressToCoords addressToCoords, HubRoleCheck hubRoleCheck) {
        if (!StringUtils.hasText(address) || addressToCoords == null) return;

        hubRoleCheck.masterCheck(); // 주소 등록, 수정은 마스터 권한으로 한정

        List<Double> coords = addressToCoords.convert(address);
        if (coords == null || coords.size() < 2) return;

        this.location = new HubLocation(address, Objects.requireNonNullElse(coords.get(0), 0.0), Objects.requireNonNullElse(coords.get(1), 0.0));
    }

    public void changeLocation(String address, HubAddressToCoords addressToCoords, HubRoleCheck hubRoleCheck) {
        setLocation(address, addressToCoords, hubRoleCheck);
    }
}