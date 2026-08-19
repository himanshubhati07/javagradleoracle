package com.example.app.dto;

import com.example.app.entity.Attendance;
import com.example.app.entity.AttendanceStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResponse(Long id, Long employeeId, String employeeName, LocalDate attendanceDate,
    AttendanceStatus attendanceStatus, LocalTime checkInTime, LocalTime checkOutTime,
    BigDecimal totalWorkingHours, String remarks) {
    public static AttendanceResponse from(Attendance a) {
        return new AttendanceResponse(a.getId(), a.getEmployee().getId(),
            a.getEmployee().getFirstName() + " " + a.getEmployee().getLastName(), a.getAttendanceDate(),
            a.getAttendanceStatus(), a.getCheckInTime(), a.getCheckOutTime(), a.getTotalWorkingHours(), a.getRemarks());
    }
}
