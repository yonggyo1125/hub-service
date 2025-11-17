package org.spartahub.hubservice.infrastructure.persistence.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class HubNotFoundException extends HttpStatusCodeException {
    public HubNotFoundException() {
        super(HttpStatus.NOT_FOUND, "허브를 찾을 수 없습니다.");
    }
}
