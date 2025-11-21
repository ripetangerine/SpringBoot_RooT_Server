package io.github._3xhaust.root_server.domain.image.exception;

import io.github._3xhaust.root_server.global.common.exception.BaseException;

public class ImageException extends BaseException {
    public ImageException(ImageErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }

    public static ImageException of(ImageErrorCode errorCode) {
        return new ImageException(errorCode, null);
    }
}

