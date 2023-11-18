package com.learning.learningproject.emp.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TaxDTO implements Serializable {
    private static final long serialVersionUID = -2363327754478188134L;

    private String empId;
    private String firstName;
    private String lastName;
    private BigDecimal YearlySalary;
    private BigDecimal taxAmount;
    private BigDecimal cessAmount;


}
