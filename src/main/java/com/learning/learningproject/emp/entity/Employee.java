package com.learning.learningproject.emp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// I am not using DB thats why commented the @Document annotation
//@Document
@Data
public class Employee {
    private String empId;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> phoneNumbers;
    private LocalDate dateOfJoining;
    private BigDecimal salary;
}
