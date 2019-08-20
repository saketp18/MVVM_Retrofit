package com.lite.main.mvvm.models;

public class ErrorResponse {

    public enum STATUS{
        SUCCESS,
        RESPONSE_FAIL,
        FAILURE
    }

    private int status;

    private String error;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
