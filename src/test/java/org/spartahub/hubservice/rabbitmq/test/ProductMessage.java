package org.spartahub.hubservice.rabbitmq.test;

import java.time.LocalDateTime;

public record ProductMessage(
        String content,
        LocalDateTime createdAt
) {}
