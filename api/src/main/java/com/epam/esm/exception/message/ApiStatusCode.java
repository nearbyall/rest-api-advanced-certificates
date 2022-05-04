package com.epam.esm.exception.message;

/**
 * API specific message status codes
 */
public enum ApiStatusCode {

    CERTIFICATE_NOT_FOUND(40401),
    CERTIFICATE_BAD_REQUEST(40001),

    TAG_NOT_FOUND(40402),
    TAG_BAD_REQUEST(40002),

    ORDER_NOT_FOUND(40403),
    ORDER_BAD_REQUEST(40003),

    USER_NOT_FOUND(40404),
    USER_BAD_REQUEST(40004);

    private final int value;

    ApiStatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
