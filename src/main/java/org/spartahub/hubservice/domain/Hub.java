package org.spartahub.hubservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.spartahub.hubservice.domain.dto.HubDto;
import org.spartahub.hubservice.infrastructure.persistence.BaseUserEntity;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 1. 허브 정보 변경은 마스터 관리자만 가능
 * 2. 허브 주소 등록/수정 위도,경도를 업데이트 하고 각 허브간 거리 계산을 한다.
 *      다만 Hub to Hub Relay를 고려하여
 *      경기남부: 경기북부, 서울, 인천, 경기 남부, 강원도
 *      대전: 충청남도, 충청북도, 세종, 대전, 전라북도, 광주, 전라남도
 *      대구: 경상북도, 대구, 경상남도, 부산, 울산
 *      는 중앙 허브로써 거치므로 각 허브의 2번째 노드가 중앙 허브를 경우하고 목적지 허브로 이동하는 거리를 계산합니다.
 *      다만 목적지 허브 까지의 직선 거리가 50KM 미만이라면 P2P 방식으로 직접 이동 거리 및 소요시간을 계산하므로 1:1 연결도 계산합니다.
 * 3. 허브 삭제는 소프트 삭제로 처리하여 기존 허브간의 이동 거리가 기록될 수 있도록 한다.
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
    @AttributeOverrides(
            @AttributeOverride(name="id", column = @Column(name="central_hub_id"))
    )
    private HubId centralHubId; // 소속된 중앙 허브 ID

    @Embedded
    private HubLocation location;

    @Builder
    public Hub(UUID hubId, String hubName, UUID centralHubId, String address, HubAddressToCoords addressToCoords, HubRoleCheck hubRoleCheck) {
        hubRoleCheck.masterCheck(); // 허브 등록 수정은 마스터 권한으로 한정

        this.id = HubId.of(hubId);
        this.centralHubId = HubId.of(centralHubId);
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

    /**
     * 허브 삭제
     *
     * @param deletedBy
     */
    public void delete(String deletedBy) {
        updateDelete(deletedBy);
    }

    public HubDto toDto() {
        return HubDto.builder()
                .id(id.getId())
                .centralHubId(centralHubId.getId())
                .hubName(hubName)
                .address(location.getAddress())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .createdAt(getCreatedAt())
                .modifiedAt(getModifiedAt())
                .build();
    }
}