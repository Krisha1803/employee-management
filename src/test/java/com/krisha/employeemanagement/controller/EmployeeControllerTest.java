package com.krisha.employeemanagement.controller;

import com.krisha.employeemanagement.entity.Employee;
import com.krisha.employeemanagement.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    // Test getting all employees
    @Test
    void testGetAllEmployees() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName("Rahul");
        employee.setLastName("Sharma");
        employee.setEmail("rahul@example.com");

        when(employeeService.getAllEmployees())
                .thenReturn(List.of(employee));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Rahul"))
                .andExpect(jsonPath("$[0].lastName").value("Sharma"))
                .andExpect(jsonPath("$[0].email").value("rahul@example.com"));
    }


    // Test getting employee by ID
    @Test
    void testGetEmployeeById() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName("Rahul");
        employee.setLastName("Sharma");
        employee.setEmail("rahul@example.com");

        when(employeeService.getEmployeeById(1L))
                .thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Rahul"))
                .andExpect(jsonPath("$.lastName").value("Sharma"));
    }

    // Test creating an employee
    @Test
    void testCreateEmployee() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName("Priya");
        employee.setLastName("Patel");
        employee.setEmail("priya@example.com");

        when(employeeService.createEmployee(
                org.mockito.ArgumentMatchers.any(Employee.class)))
                .thenReturn(employee);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .post("/api/employees")
                                .contentType(
                                        org.springframework.http.MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "firstName": "Priya",
                                "lastName": "Patel",
                                "email": "priya@example.com",
                                "department": "HR",
                                "designation": "HR Executive",
                                "salary": 45000
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Priya"))
                .andExpect(jsonPath("$.email").value("priya@example.com"));
    }

    // Test updating an employee
    @Test
    void testUpdateEmployee() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName("Rahul");
        employee.setLastName("Sharma");
        employee.setEmail("rahul.updated@example.com");
        employee.setDesignation("Senior Developer");

        when(employeeService.updateEmployee(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any(Employee.class)))
                .thenReturn(employee);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .put("/api/employees/1")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "firstName": "Rahul",
                                "lastName": "Sharma",
                                "email": "rahul.updated@example.com",
                                "department": "IT",
                                "designation": "Senior Developer",
                                "salary": 75000
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")
                        .value("rahul.updated@example.com"))
                .andExpect(jsonPath("$.designation")
                        .value("Senior Developer"));
    }

    // Test deleting an employee
    @Test
    void testDeleteEmployee() throws Exception {

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }

    // Test validation error
    @Test
    void testCreateEmployeeWithInvalidData() throws Exception {

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .post("/api/employees")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "firstName": "",
                                "lastName": "Sharma",
                                "email": "invalid-email",
                                "department": "IT",
                                "designation": "Developer",
                                "salary": -5000
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.salary").exists());
    }

    // Test employee not found
    @Test
    void testGetEmployeeNotFound() throws Exception {

        when(employeeService.getEmployeeById(9999L))
                .thenThrow(new com.krisha.employeemanagement.exception
                        .EmployeeNotFoundException(
                        "Employee not found with ID: 9999"));

        mockMvc.perform(get("/api/employees/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("Employee not found with ID: 9999"));
    }




}