package org.spartahub.hubservice.infrastructure.api;

import org.junit.jupiter.api.Test;
import org.spartahub.hubservice.domain.Hub;
import org.spartahub.hubservice.domain.HubRepository;
import org.spartahub.hubservice.domain.HubRouteEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class KakaoHubRouteEstimatorTest {
    @Autowired
    HubRepository repository;

    @Autowired
    HubRouteEstimator estimator;

    @Test
    void hubRouteEstimatorTest() {
        List<Hub> hubs = repository.findAll();

        double[] item = estimator.estimate(hubs);
        System.out.println("결과: " + Arrays.toString(item));
    }
}
