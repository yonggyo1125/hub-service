package org.spartahub.hubservice.infrastructure.commandline;

import lombok.RequiredArgsConstructor;
import org.spartahub.hubservice.application.hub.HubCreateService;
import org.spartahub.hubservice.domain.Hub;
import org.spartahub.hubservice.domain.HubRepository;
import org.spartahub.hubservice.domain.QHub;
import org.spartahub.hubservice.infrastructure.security.UserDetailsImpl;
import org.spartahub.hubservice.presentation.dto.HubRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InsertHub implements CommandLineRunner {
    private final HubCreateService hubCreateService;
    private final HubRepository hubRepository;

    @Override
    public void run(String... args) throws Exception {
        insertHub();
    }

    @Async("simpleTaskExecutor")
    public void insertHub(){
        UserDetails userDetails = UserDetailsImpl.builder()
                .uuid(UUID.randomUUID())
                .username("commandliner")
                .roles("ROLE_MASTER")
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // 등록된 허브가 없다면 허브 등록 시작
        if (hubRepository.count() == 0L) {

            /**
             * 중앙 허브는 centerHubId 값이 null
             *
             */
            List<HubRequest> centerHubs = List.of(
                    new HubRequest("경기 남부 센터", null, "경기도 이천시 덕평로 257-21"),
                    new HubRequest("대전광역시 센터", null, "대전 서구 둔산로 100"),
                    new HubRequest("대구광역시 센터", null, "대구 북구 태평로 161")
            );


            System.out.println("*".repeat(50) + " 중앙 허브 등록 시작 " + "*".repeat(50));
            // 허브 등록
            centerHubs.forEach(hubCreateService::create);
            System.out.println("*".repeat(50) + "중앙 허브 등록 완료 " + "*".repeat(50));

            // 경기 남부 센터 허브 ID
            QHub hub = QHub.hub;
            Hub center1 = hubRepository.findOne(hub.hubName.eq("경기 남부 센터")).orElse(null);

            // 경기 남부 센터 소속 허브 등록 - 경기북부, 서울, 인천, 경기남부, 강원도
            if (center1 != null) {
                System.out.println("*".repeat(50) + " 경기 남부 센터 소속 허브 등록 시작 " + "*".repeat(50));
                Long center1HubId = center1.getHubId();

                System.out.println("*".repeat(50) + " 경기 남부 센터 소속 허브 등록 완료 " + "*".repeat(50));
            }

            // 대전 광역시 센터 소속 허브 등록 - 충청남도, 충청북도, 세종, 대전, 전라북도, 광주, 전라남도
            System.out.println("*".repeat(50) + " 대전 광역시 센터 소속 허브 등록 시작 " + "*".repeat(50));

            System.out.println("*".repeat(50) + " 대전 광역시 센터 소속 허브 등록 완료 " + "*".repeat(50));

            // 대구 광역시 센터 소속 허브 등록 - 경상북도, 대구, 경상남도, 부산, 울산
            System.out.println("*".repeat(50) + " 대구 광역시 센터 소속 허브 등록 시작 " + "*".repeat(50));

            System.out.println("*".repeat(50) + " 대구 광역시 센터 소속 허브 등록 완료 " + "*".repeat(50));
        }
    }
}
