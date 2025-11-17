package org.spartahub.hubservice.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spartahub.hubservice.application.hub.HubCreateService;
import org.spartahub.hubservice.application.hub.HubDeleteService;
import org.spartahub.hubservice.domain.hub.HubId;
import org.spartahub.hubservice.domain.hub.dto.HubDto;
import org.spartahub.hubservice.infrastructure.persistence.hub.HubDetailsDao;
import org.spartahub.hubservice.presentation.dto.HubCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HubController {
    private final HubCreateService createService;
    private final HubDeleteService deleteService;
    private final HubDetailsDao detailsDao;

    /**
     * 허브 등록
     * @param request
     * @return
     */
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, UUID> createHub(@Valid @RequestBody HubCreateRequest request) {
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
    public HubDto getHub(@PathVariable("hubId") UUID hubId) {
        return detailsDao.findById(HubId.of(hubId));
    }

    /**
     * 허브 목록 조회
     *
     * @param hubIds
     * @return
     */
    @GetMapping("items")
    public List<HubDto> getHubs(@RequestParam(name="hubId", required = false) List<UUID> hubIds) {
        return detailsDao.findAllByUUID(hubIds);
    }

    /**
     * 허브 삭제
     *
     * @param hubId
     */
    @DeleteMapping("{hubId}")
    public void deleteHub(@PathVariable("hubId") UUID hubId, @AuthenticationPrincipal UserDetails userDetails) {
        deleteService.delete(userDetails.getUsername(), hubId);
    }
}
