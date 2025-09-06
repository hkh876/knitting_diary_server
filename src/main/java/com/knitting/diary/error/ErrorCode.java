package com.knitting.diary.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND_DATA_ERROR(HttpStatus.NOT_FOUND, "데이터가 존재 하지 않습니다."),
    NOT_FOUND_FILE_ERROR(HttpStatus.NOT_FOUND, "파일이 존재 하지 않습니다."),
    UPLOAD_SIZE_ERROR(HttpStatus.BAD_REQUEST, "파일 업로드 용량을 초과했습니다."),
    NOT_VALID_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 오류 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
