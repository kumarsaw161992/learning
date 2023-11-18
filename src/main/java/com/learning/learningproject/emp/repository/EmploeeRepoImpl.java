package com.learning.learningproject.emp.repository;

import com.learning.learningproject.emp.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmploeeRepoImpl implements EmployeeRepo{

    public static List<Employee> employeeList = new ArrayList<>();

    @Override
    public Employee save(Employee employee) {
        employeeList.add(employee);
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        return employeeList;
    }
}
