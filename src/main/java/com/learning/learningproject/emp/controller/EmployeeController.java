package com.learning.learningproject.emp.controller;

import com.learning.learningproject.emp.dto.EmployeeDTO;
import com.learning.learningproject.emp.dto.EmployeeResponse;
import com.learning.learningproject.emp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /* sample request
    {
    "empId": "emp007",
    "firstName": "tiya",
    "lastName": "marko",
    "email": "marko@gmail.com",
    "phoneNumbers":["9934567890","8934567890"],
    "dateOfJoining": "2018-01-10",
    "salary": 62500
    }
     */

    @PostMapping("/add")
    public EmployeeResponse addEmploye(@RequestBody EmployeeDTO employee){
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/tax")
    public EmployeeResponse getTaxDetails(){
        return employeeService.getTaxDetails();
    }
}
