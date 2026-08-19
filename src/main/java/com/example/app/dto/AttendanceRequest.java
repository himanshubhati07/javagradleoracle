package com.example.app.dto;

import com.example.app.entity.AttendanceStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRequest(
    @NotNull @Positive Long employeeId,
    @NotNull LocalDate attendanceDate,
    @NotNull AttendanceStatus attendanceStatus,
    LocalTime checkInTime,
    LocalTime checkOutTime,
    @Size(max=500) String remarks) {}
