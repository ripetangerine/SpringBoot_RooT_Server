package io.github._3xhaust.root_server.global.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMessage();
    HttpStatus getHttpStatus();
    String getCode();
}



