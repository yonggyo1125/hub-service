package org.spartahub.hubservice.presentation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spartahub.hubservice.infrastructure.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HubController {

    private final Logger log = LoggerFactory.getLogger(HubController.class);

    @GetMapping("test")
    public void test(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("userDetails:{}", userDetails);

    }
}
