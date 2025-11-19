package org.spartahub.hubservice.infrastructure.feignclient;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@EnableFeignClients("org.spartahub.hubservice")
@RequiredArgsConstructor
public class FeighConfig {

    /**
     * OpenFeign 요청시 JWT 토큰이 있다면 함께 전송
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor() {

        return tpl -> {
            RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
            if (attrs instanceof ServletRequestAttributes) {
                HttpServletRequest req = ((ServletRequestAttributes) attrs).getRequest();
                String authorization = req.getHeader("Authorization");
                if (StringUtils.hasText(authorization)) {
                    tpl.header("Authorization", authorization);
                }
            }

        };
    }

}
