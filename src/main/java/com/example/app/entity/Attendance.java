package com.example.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance_records", uniqueConstraints = @UniqueConstraint(name = "uk_attendance_employee_date", columnNames = {"employee_id", "attendance_date"}))
public class Attendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false) private Employee employee;
    @Column(name = "attendance_date", nullable = false) private LocalDate attendanceDate;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private AttendanceStatus attendanceStatus;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    @Column(precision = 6, scale = 2, nullable = false) private BigDecimal totalWorkingHours = BigDecimal.ZERO;
    @Column(length = 500) private String remarks;

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; } public void setEmployee(Employee v) { employee = v; }
    public LocalDate getAttendanceDate() { return attendanceDate; } public void setAttendanceDate(LocalDate v) { attendanceDate = v; }
    public AttendanceStatus getAttendanceStatus() { return attendanceStatus; } public void setAttendanceStatus(AttendanceStatus v) { attendanceStatus = v; }
    public LocalTime getCheckInTime() { return checkInTime; } public void setCheckInTime(LocalTime v) { checkInTime = v; }
    public LocalTime getCheckOutTime() { return checkOutTime; } public void setCheckOutTime(LocalTime v) { checkOutTime = v; }
    public BigDecimal getTotalWorkingHours() { return totalWorkingHours; } public void setTotalWorkingHours(BigDecimal v) { totalWorkingHours = v; }
    public String getRemarks() { return remarks; } public void setRemarks(String v) { remarks = v; }
}
