package com.sheep.ezloan.support.error;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public enum ErrorType {

    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "An unexpected error has occurred.",
            LogLevel.ERROR),
    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Bad Request Exception", LogLevel.ERROR),

    // @RequestBody 데이터 미 존재
    REQUEST_BODY_MISSING_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Required request body is missing",
            LogLevel.ERROR),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, ErrorCode.E400, " Invalid Type Value", LogLevel.ERROR),

    // Request Parameter 로 데이터가 전달되지 않을 경우
    MISSING_REQUEST_PARAMETER_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400,
            "Missing Servlet RequestParameter Exception", LogLevel.ERROR),

    // 입력/출력 값이 유효하지 않음
    IO_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "I/O Exception", LogLevel.ERROR),

    // com.google.gson JSON 파싱 실패
    JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "JsonParseException", LogLevel.ERROR),

    // com.fasterxml.jackson.core Processing Error
    JACKSON_PROCESS_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "com.fasterxml.jackson.core Exception",
            LogLevel.ERROR),

    // 토큰이 유효하지 않음
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, ErrorCode.E401, "Unauthorized token", LogLevel.ERROR),

    // 권한이 없음
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN, ErrorCode.E403, "Forbidden", LogLevel.ERROR),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, ErrorCode.E404, "Not Found Exception", LogLevel.ERROR),

    // NULL Point Exception 발생
    NULL_POINT_ERROR(HttpStatus.NOT_FOUND, ErrorCode.E404, "Null Point Exception", LogLevel.ERROR),

    // @RequestBody 및 @RequestParam, @PathVariable, @RequestHeader 값이 유효하지 않음
    NOT_VALID_ERROR(HttpStatus.NOT_FOUND, ErrorCode.E404, "handle Validation Exception", LogLevel.ERROR),

    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "Internal Server Error Exception",
            LogLevel.ERROR);

    private final HttpStatus status;

    private final ErrorCode code;

    private final String message;

    private final LogLevel logLevel;

    ErrorType(HttpStatus status, ErrorCode code, String message, LogLevel logLevel) {

        this.status = status;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

}
