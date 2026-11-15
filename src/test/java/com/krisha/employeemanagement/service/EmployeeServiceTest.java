
package com.krisha.employeemanagement.service;

import com.krisha.employeemanagement.entity.Employee;
import com.krisha.employeemanagement.exception.EmployeeNotFoundException;
import com.krisha.employeemanagement.repository.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    // Test creating an employee
    @Test
    void testCreateEmployee() {

        Employee employee = new Employee();
        employee.setFirstName("Rahul");
        employee.setLastName("Sharma");
        employee.setEmail("rahul@example.com");
        employee.setSalary(new BigDecimal("65000"));

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);

        assertNotNull(result);
        assertEquals("Rahul", result.getFirstName());

        verify(employeeRepository, times(1)).save(employee);
    }

    // Test getting an employee by ID
    @Test
    void testGetEmployeeById() {

        Employee employee = new Employee();
        employee.setFirstName("Rahul");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals("Rahul", result.getFirstName());
    }

    // Test employee not found
    @Test
    void testGetEmployeeByIdNotFound() {

        when(employeeRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(999L));
    }

    // Test updating an employee
    @Test
    void testUpdateEmployee() {

        Employee existingEmployee = new Employee();
        existingEmployee.setFirstName("Rahul");
        existingEmployee.setLastName("Sharma");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Rahul");
        updatedEmployee.setLastName("Sharma");
        updatedEmployee.setEmail("rahul.updated@example.com");
        updatedEmployee.setDepartment("IT");
        updatedEmployee.setDesignation("Senior Developer");
        updatedEmployee.setSalary(new BigDecimal("75000"));

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(employeeRepository.save(existingEmployee))
                .thenReturn(existingEmployee);

        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        assertEquals("rahul.updated@example.com", result.getEmail());
        assertEquals("Senior Developer", result.getDesignation());

        verify(employeeRepository, times(1)).save(existingEmployee);
    }

    // Test deleting an employee
    @Test
    void testDeleteEmployee() {

        Employee employee = new Employee();
        employee.setFirstName("Rahul");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).delete(employee);
    }
}
