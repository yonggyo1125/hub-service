package org.spartahub.hubservice.presentation.exception;

import org.springframework.http.HttpStatusCode;

public record ErrorResponse(
        HttpStatusCode status,
        Object message
) {}
