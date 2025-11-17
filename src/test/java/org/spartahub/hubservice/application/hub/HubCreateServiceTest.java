package org.spartahub.hubservice.application.hub;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.spartahub.hubservice.domain.hub.dto.HubDto;
import org.spartahub.hubservice.presentation.dto.HubCreateRequest;
import org.spartahub.hubservice.test.MockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class HubCreateServiceTest {
    @Autowired
    HubCreateService createService;

    @Test
    @DisplayName("허브 등록 테스트")
    @MockUser(roles="MASTER")
    void createTest() {
        HubCreateRequest request = new HubCreateRequest("서울특별시 센터", "서울특별시 송파구 송파대로 55");
        HubDto dto = createService.create(request);
        System.out.println("등록된 허브:" + dto);
    }
}
