package io.github._3xhaust.root_server.domain.image.exception;

import io.github._3xhaust.root_server.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ImageErrorCode implements ErrorCode {
    IMAGE_NOT_FOUND("IMAGE_NOT_FOUND", HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다."),
    INVALID_IMAGE_TYPE("INVALID_IMAGE_TYPE", HttpStatus.BAD_REQUEST, "지원하지 않는 이미지 형식입니다."),
    IMAGE_UPLOAD_FAILED("IMAGE_UPLOAD_FAILED", HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다."),
    IMAGE_DELETE_FAILED("IMAGE_DELETE_FAILED", HttpStatus.INTERNAL_SERVER_ERROR, "이미지 삭제에 실패했습니다."),
    IMAGE_SIZE_EXCEEDED("IMAGE_SIZE_EXCEEDED", HttpStatus.BAD_REQUEST, "허용된 이미지 용량을 초과했습니다."),
    IMAGE_COUNT_EXCEEDED("IMAGE_COUNT_EXCEEDED", HttpStatus.BAD_REQUEST, "허용된 이미지 개수를 초과했습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}

