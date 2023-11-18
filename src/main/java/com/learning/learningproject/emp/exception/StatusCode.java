package com.learning.learningproject.emp.exception;

public enum StatusCode {

    SUCCESS("200", "OK"),
    FAILURE("600", "KO"),
    DATA_NOT_FOUND("601", "Data Not Found"),
    INVALID_INPUT("602", "Invalid input");

    private final String code;
    private final String desc;

    StatusCode(String code, String value) {
        this.code = code;
        this.desc = value;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
