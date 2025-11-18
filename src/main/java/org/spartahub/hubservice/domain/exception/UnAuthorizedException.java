package org.spartahub.hubservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class UnAuthorizedException extends HttpStatusCodeException  {
    private String field;
    private String message;

    public UnAuthorizedException() {
        this("접근 권한이 없습니다.");
    }

    public UnAuthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnAuthorizedException(String field, String message) {
        this(message);
        this.message = message;
        this.field = field;
    }
}
