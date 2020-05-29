package com.dictionary.domain;

public enum HttpStatus {

    NO_CONTENT(204, "No Content"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    NULL(-1, "NULL");

    private final int number;
    private final String value;

    HttpStatus(int number, String value) {
        this.number = number;
        this.value = value;
    }

}

