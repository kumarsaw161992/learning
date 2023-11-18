package com.learning.learningproject.emp.service;

import com.learning.learningproject.emp.dto.EmployeeDTO;
import com.learning.learningproject.emp.dto.EmployeeResponse;
import com.learning.learningproject.emp.dto.TaxDTO;
import com.learning.learningproject.emp.entity.Employee;
import com.learning.learningproject.emp.exception.EmployeeException;
import com.learning.learningproject.emp.exception.StatusCode;
import com.learning.learningproject.emp.repository.EmployeeRepo;
import com.learning.learningproject.emp.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {
    public static final BigDecimal TWELVE_THOSAND_FIVE_HUNDREDS = new BigDecimal(12500);
    public static final BigDecimal FIFTY_THOSANDS = new BigDecimal(50000);
    public static final BigDecimal TWO_LACS_FIFTY_THOUSANDS = new BigDecimal(2_50_000);
    public static final BigDecimal FIVE_LACS = new BigDecimal(5_00_000);
    public static final BigDecimal TEN_LACS = new BigDecimal(10_00_000);
    public static final BigDecimal TWENTY_FIVE_LACS = new BigDecimal(25_00_000);
    public static final BigDecimal FIVE = new BigDecimal(5);
    public static final BigDecimal TWO = new BigDecimal(2);
    public static final BigDecimal TEN = new BigDecimal(10);
    public static final BigDecimal TWELVE = new BigDecimal(12);
    public static final BigDecimal THIRTY = new BigDecimal(30);
    public static final BigDecimal HUNDRED = new BigDecimal(100);


    @Autowired
    private EmployeeRepo employeeRepo;

    public EmployeeResponse addEmployee(EmployeeDTO dto) {
        EmployeeResponse mappingResponse = new EmployeeResponse<EmployeeDTO>();
        try {
            validateRequest(dto);
            Employee save = Mapper.copyPorps(dto, new Employee());
            save = employeeRepo.save(save);
            EmployeeDTO responseDTO = Mapper.copyPorps(save, new EmployeeDTO());
            mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
            mappingResponse.setMapping(responseDTO);
        } catch (Exception e) {
            log.error("Excption while adding employee", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    private void validateRequest(EmployeeDTO dto) throws EmployeeException {
        if (StringUtils.isEmpty(dto.getEmpId()) ||
                StringUtils.isEmpty(dto.getFirstName()) ||
                StringUtils.isEmpty(dto.getLastName()) ||
                StringUtils.isEmpty(dto.getEmail()) ||  // need to validate email format.
                dto.getPhoneNumbers().isEmpty() ||      //Phone number need to be validated
                dto.getDateOfJoining() == null ||        //need to validate for invalid date
                dto.getSalary() == null) {                // need to validate salary ammount
            throw new EmployeeException(StatusCode.INVALID_INPUT.getCode(), "All Fields are mandatory");
        }
    }

    public EmployeeResponse getTaxDetails() {
        EmployeeResponse mappingResponse = new EmployeeResponse<TaxDTO>();
        try {
            List<Employee> allEmployees = employeeRepo.findAll();
            List<TaxDTO> taxDTOList = allEmployees.stream()
                    .map(employee -> calculateTax(employee)).collect(Collectors.toList());
            mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
            mappingResponse.setMappingList(taxDTOList);
        } catch (Exception exp) {
            log.error("Excption while getting Tax Details ", exp);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(exp.getMessage());
        }
        return mappingResponse;
    }

    private TaxDTO calculateTax(Employee employee) {
        TaxDTO taxDTO = Mapper.copyPorps(employee, new TaxDTO());
        taxDTO.setYearlySalary(employee.getSalary().multiply(new BigDecimal(12)));

        BigDecimal totalSalary, tax, cess;
        int currentYear = LocalDate.now().getYear();
        int empJoiningYear = employee.getDateOfJoining().getYear();
        int empJoiningMonth = employee.getDateOfJoining().getMonthValue();

        if (empJoiningYear == currentYear && empJoiningMonth > 3) { //if employee joins after march
            Period period = Period.between(employee.getDateOfJoining(), LocalDate.of(currentYear + 1, 3, 31));
            int totalTaxableDays = period.getDays();
            log.info("totalTaxableDays:", totalTaxableDays);
            totalSalary = employee.getSalary().divide(THIRTY).multiply(new BigDecimal(totalTaxableDays));
        } else {
            totalSalary = employee.getSalary().multiply(TWELVE);
        }

        if (totalSalary.compareTo(TWO_LACS_FIFTY_THOUSANDS) > 0 && totalSalary.compareTo(FIVE_LACS) <= 0) {
            taxDTO.setTaxAmount(totalSalary.subtract(TWO_LACS_FIFTY_THOUSANDS).multiply(FIVE).divide(HUNDRED));
        } else if (totalSalary.compareTo(FIVE_LACS) > 0 && totalSalary.compareTo(TEN_LACS) <= 0) {
            taxDTO.setTaxAmount(totalSalary.subtract(FIVE_LACS).multiply(TEN).divide(HUNDRED).add(TWELVE_THOSAND_FIVE_HUNDREDS));
        } else if (totalSalary.compareTo(TEN_LACS) > 0) {
            taxDTO.setTaxAmount(totalSalary.subtract(TEN_LACS).multiply(TEN).divide(HUNDRED).add(TWELVE_THOSAND_FIVE_HUNDREDS).add(FIFTY_THOSANDS));
        } else {
            taxDTO.setTaxAmount(new BigDecimal(0));
            taxDTO.setCessAmount(new BigDecimal(0));
        }

        if(totalSalary.compareTo(TWENTY_FIVE_LACS)>0){
            taxDTO.setCessAmount(totalSalary.subtract(TWENTY_FIVE_LACS).multiply(TWO).divide(HUNDRED));
        }
        return taxDTO;
    }
}
