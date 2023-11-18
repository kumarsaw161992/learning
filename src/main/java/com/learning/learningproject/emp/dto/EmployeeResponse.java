package com.learning.learningproject.emp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse<T> {

    private String status;
    private String error;
    private T mapping;
    private List<T> mappingList;

}
