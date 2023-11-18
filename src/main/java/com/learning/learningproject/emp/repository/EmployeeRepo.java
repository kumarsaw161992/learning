package com.learning.learningproject.emp.repository;

import com.learning.learningproject.emp.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

// commented because i am not ussing DB
//@Repository

public interface EmployeeRepo { //extend MongoRepository{
    Employee save(Employee employee);

    List<Employee> findAll();
}
