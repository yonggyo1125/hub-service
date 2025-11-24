package org.spartahub.hubservice.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spartahub.hubservice.application.hub.HubCreateService;
import org.spartahub.hubservice.application.hub.HubDeleteService;
import org.spartahub.hubservice.domain.HubDetailsRepository;
import org.spartahub.hubservice.domain.dto.HubDto;
import org.spartahub.hubservice.presentation.dto.HubRequest;
import org.spartahub.hubservice.presentation.dto.HubResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HubController {
    private final HubCreateService createService;
    private final HubDeleteService deleteService;
    private final HubDetailsRepository detailsRepository;

    /**
     * 허브 등록
     * @param request
     * @return
     */
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> createHub(@Valid @RequestBody HubRequest request) {
        HubDto hub = createService.create(request);

        return Map.of("id", hub.id());
    }

    /**
     * 허브 하나 조회
     *
     * @param hubId
     * @return
     */
    @GetMapping("{hubId}/retrieval")
    public HubResponse getHub(@PathVariable("hubId") Long hubId) {

        HubDto item = detailsRepository.findById(hubId);

        return toResponse(item);

    }

    /**
     * 허브 목록 조회
     *
     * @param hubIds
     * @return
     */
    @GetMapping("items")
    public List<HubResponse> getHubs(@RequestParam(name="hubId", required = false) List<Long> hubIds) {
        List<HubDto> items = detailsRepository.findAll(hubIds);

        return items == null ? List.of() : items.stream().map(this::toResponse).toList();
    }

    /**
     * 허브 삭제
     *
     * @param hubId
     */
    @GetMapping("{hubId}/delete")
    public void deleteHub(@PathVariable("hubId") Long hubId, @AuthenticationPrincipal UserDetails userDetails) {
        deleteService.delete(userDetails.getUsername(), hubId);
    }


    // HubDto -> HubResponse
    private HubResponse toResponse(HubDto item) {
        return HubResponse.builder()
                .id(item.id())
                .hubName(item.hubName())
                .latitude(item.latitude())
                .longitude(item.longitude())
                .address(item.address())
                .createdAt(item.createdAt())
                .modifiedAt(item.modifiedAt())
                .build();
    }
}
