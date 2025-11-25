package org.spartahub.hubservice.domain;

import java.util.List;

/**
 * 허브 다중 경유 총 거리 및 소요 시간 계산
 * - 소요 시간은 hour 단위, 거리는 km 단위로 계산
 * - 0번째는 소요시간, 1번째는 거리
 */
public interface HubRouteEstimator {
    double[] estimate(List<Hub> hubs);
}
