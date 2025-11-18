package org.spartahub.hubservice.domain;

import java.util.List;

/**
 * 허브의 주소를 위도, 경도 좌표로 변경
 *
 */
public interface HubAddressToCoords {
    List<Double> convert(String address);
}
