package com.learning.learningproject.emp.exception;

public class EmployeeException extends Exception{

    private String code;
    private String desc;

    public EmployeeException(String code, String desc) {
        super(desc);
        this.code = code;
        this.desc = desc;
    }
}
