package inflearn_java_middle.enumeration.test.http;

import inflearn_java_middle.enumeration.test.AuthGrade;

public enum HttpStatus {
    OK(200, "OK", true),
    BAD_REQUEST(400, "Bad Request", false),
    NOT_FOUND(404, "Not Found", false),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", false);

    private final int code;
    private final String message;
    private final boolean success;

    HttpStatus(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public AuthGrade findByCode(int httpCode) {
        return AuthGrade.valueOf(String.valueOf(httpCode));
    }
}
