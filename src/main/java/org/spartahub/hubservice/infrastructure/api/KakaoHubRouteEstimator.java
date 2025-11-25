package org.spartahub.hubservice.infrastructure.api;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.spartahub.hubservice.domain.Hub;
import org.spartahub.hubservice.domain.HubRouteEstimator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class KakaoHubRouteEstimator implements HubRouteEstimator {

    @Value("${kakao.restapi.key}")
    private String apiKey;

    @Override
    public double[] estimate(List<Hub> hubs) {
        if (hubs == null || hubs.isEmpty() || hubs.size() < 2) {
            return null;
        }

        Map<String, Object> params = new HashMap<>();
        Hub departure = hubs.getFirst(); // 출발지 허브
        Hub arrival = hubs.getLast(); // 도착지 허브

        // 출발지
        params.put("origin", Map.of("name", departure.getHubName(), "x", departure.getLocation().getLongitude(), "y", departure.getLocation().getLatitude()));

        // 도착지
        params.put("destination", Map.of("name", arrival.getHubName(), "x", arrival.getLocation().getLongitude(), "y", arrival.getLocation().getLatitude()));

        // 경유지
        if (hubs.size() > 2) {
            List<Map<String, Object>> waypoints = hubs.subList(1, hubs.size() - 1)
                    .stream()
                    .map(hub -> {
                        Map<String, Object> item = new HashMap<>();
                        item.put("name", hub.getHubName());
                        item.put("x", hub.getLocation().getLongitude());
                        item.put("y", hub.getLocation().getLatitude());
                        return item;
                    }).toList();
            params.put("waypoints", waypoints);
        }

        ResponseEntity<JsonNode> response = RestClient.builder()
                .baseUrl("https://apis-navi.kakaomobility.com/v1/waypoints/directions")
                .build()
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "KakaoAK " + apiKey
                )
                .body(params)
                .retrieve()
                .toEntity(JsonNode.class);


        if (response.getStatusCode().is2xxSuccessful()) {
            double distance = 0.0, duration = 0.0;
            try {
                JsonNode nodes = response.getBody();
                JsonNode routes = nodes.get("routes");
                if (routes != null && routes.isArray()) {
                    for (JsonNode route : routes) {
                        distance += route.get("summary").get("distance").asDouble(0.0);
                        duration += route.get("summary").get("duration").asDouble(0.0);

                    }
                }
                return new double[] {distance / 1000.0, duration / 1000.0};
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }
}
