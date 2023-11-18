package com.learning.learningproject.emp.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = -2363327754478188163L;

    private String empId;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> phoneNumbers;
    private LocalDate dateOfJoining;
    private BigDecimal salary;

}
