package org.spartahub.hubservice.presentation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("profile")
    String getUserProfile();
}