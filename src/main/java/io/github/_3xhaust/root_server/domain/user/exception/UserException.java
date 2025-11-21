package io.github._3xhaust.root_server.domain.user.exception;

import io.github._3xhaust.root_server.global.common.exception.BaseException;

public class UserException extends BaseException {
    public UserException(UserErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
