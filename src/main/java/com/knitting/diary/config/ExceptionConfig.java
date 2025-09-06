package com.knitting.diary.config;

import com.knitting.diary.error.ErrorCode;
import com.knitting.diary.error.ErrorResDto;
import com.knitting.diary.exception.BaseException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionConfig {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<String> errorMessages = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ErrorCode errorCode = ErrorCode.NOT_VALID_ERROR;

        return errorResponse(exception, errorCode.getHttpStatus(), request, errorCode, errorMessages.toString());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception, WebRequest request, HttpServletResponse response) {
        // CORS 오류 해결
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");

        ErrorCode errorCode = ErrorCode.UPLOAD_SIZE_ERROR;
        return errorResponse(exception, errorCode.getHttpStatus(), request, errorCode, errorCode.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaeException(BaseException exception, WebRequest request) {
        ErrorCode errorCode = exception.getErrorCode();
        return errorResponse(exception, errorCode.getHttpStatus(), request, errorCode, errorCode.getMessage());
    }

    private ResponseEntity<Object> errorResponse(Exception exception, HttpStatus status, WebRequest request, ErrorCode errorCode,
                                                 String message) {
        log.error("[{}] [{}] {}", request.getDescription(true), exception.getClass().getSimpleName(), message);

        ErrorResDto resDto = ErrorResDto.builder()
                .errorCode(errorCode)
                .message(message)
                .build();
        return ResponseEntity.status(status).body(resDto);
    }

}
