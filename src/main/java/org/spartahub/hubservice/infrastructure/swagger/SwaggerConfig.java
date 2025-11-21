package org.spartahub.hubservice.infrastructure.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SwaggerConfig {

    private final String PREFIX = "/v1/hub";

    @Bean
    public OpenAPI openAPI(@Value("${openapi.service.url}") String url) {
        return new OpenAPI()
                .servers(List.of(new Server().url(url)))
                .components(new Components().addSecuritySchemes("Bearer", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer"))
                .info(new Info().title("스파르타 허브")
                        .description("HUB API"));
    }

    @Bean
    public OpenApiCustomizer addPrefixToPaths() {
        return openApi -> {
            Paths paths = openApi.getPaths();
            if (paths == null) return;

            // 기존 paths 를 복사하면서 prefix 붙여서 추가
            Map<String, PathItem> original = new LinkedHashMap<>(paths);
            for (String path : original.keySet().toArray(new String[0])) {
                String prefixed = PREFIX + path;
                // 이미 같은 prefixed 경로가 없을 때만 추가
                if (!paths.containsKey(prefixed)) {
                    PathItem item = original.get(path);
                    // PathItem은 mutable 객체이므로 안전을 위해 복제할 필요가 있을 수 있음.
                    // 간단한 경우 그대로 사용해도 UI에 표시됨.
                    paths.addPathItem(prefixed, item);
                }
            }

            // 원래 경로를 숨기고 싶다면 아래처럼 제거 가능
            original.keySet().forEach(s -> {
                if (!s.startsWith(PREFIX)) paths.remove(s);
            });
        };
    }
}
