package com.epam.esm.exception;

import java.util.Objects;

public class ApiResponse {

    private final String message;
    private final int status;

    public ApiResponse(String message, int apiStatusCode) {
        this.message = message;
        this.status = apiStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiResponse that = (ApiResponse) o;

        if (status != that.status) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + status;
        return result;
    }

    @Override
    public String toString() {
        return "ApiErrorResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
