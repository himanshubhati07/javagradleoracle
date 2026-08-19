package com.example.app.dto;

import com.example.app.entity.Employee;
import com.example.app.entity.EmploymentStatus;
import java.time.LocalDate;

public record EmployeeResponse(Long id, String firstName, String lastName, String email,
    String phoneNumber, String department, String designation, LocalDate joiningDate,
    EmploymentStatus employmentStatus) {
    public static EmployeeResponse from(Employee e) {
        return new EmployeeResponse(e.getId(), e.getFirstName(), e.getLastName(), e.getEmail(),
            e.getPhoneNumber(), e.getDepartment(), e.getDesignation(), e.getJoiningDate(), e.getEmploymentStatus());
    }
}
