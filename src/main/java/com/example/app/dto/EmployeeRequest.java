package com.example.app.dto;

import com.example.app.entity.EmploymentStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EmployeeRequest(
    @NotBlank @Size(max=80) String firstName,
    @NotBlank @Size(max=80) String lastName,
    @NotBlank @Email @Size(max=160) String email,
    @NotBlank @Pattern(regexp="^[+0-9() -]{7,30}$") String phoneNumber,
    @NotBlank @Size(max=100) String department,
    @NotBlank @Size(max=100) String designation,
    @NotNull @PastOrPresent LocalDate joiningDate,
    @NotNull EmploymentStatus employmentStatus) {}
