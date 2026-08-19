package com.example.app.service;

import com.example.app.dto.*;
import com.example.app.entity.*;
import com.example.app.exception.*;
import com.example.app.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.*;
import java.time.*;
import java.util.List;

@Service
@Transactional
public class AttendanceService {
    private final AttendanceRepository repository;
    private final EmployeeRepository employeeRepository;
    public AttendanceService(AttendanceRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository; this.employeeRepository = employeeRepository;
    }
    public AttendanceResponse create(AttendanceRequest r) {
        if (repository.existsByEmployeeIdAndAttendanceDate(r.employeeId(), r.attendanceDate())) throw new ConflictException("Attendance already exists for employee on this date");
        Attendance a = apply(new Attendance(), r);
        return AttendanceResponse.from(repository.save(a));
    }
    @Transactional(readOnly=true) public AttendanceResponse get(Long id) { return AttendanceResponse.from(find(id)); }
    @Transactional(readOnly=true) public List<AttendanceResponse> search(Long employeeId, LocalDate date, String employeeName, Long afterId, int limit) {
        int safeLimit = Math.min(Math.max(limit, 1), 20);
        String name = employeeName == null || employeeName.isBlank() ? null : employeeName.trim();
        return repository.search(afterId == null ? 0L : afterId, employeeId, date, name, PageRequest.of(0, safeLimit)).stream().map(AttendanceResponse::from).toList();
    }
    public AttendanceResponse update(Long id, AttendanceRequest r) {
        if (repository.existsByEmployeeIdAndAttendanceDateAndIdNot(r.employeeId(), r.attendanceDate(), id)) throw new ConflictException("Attendance already exists for employee on this date");
        return AttendanceResponse.from(repository.save(apply(find(id), r)));
    }
    public void delete(Long id) { repository.delete(find(id)); }
    private Attendance find(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendance " + id + " not found")); }
    private Attendance apply(Attendance a, AttendanceRequest r) {
        Employee employee = employeeRepository.findById(r.employeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee " + r.employeeId() + " not found"));
        if ((r.checkInTime() == null) != (r.checkOutTime() == null)) throw new BadRequestException("Check-in and check-out times must both be provided or both omitted");
        BigDecimal hours = BigDecimal.ZERO;
        if (r.checkInTime() != null) {
            long minutes = Duration.between(r.checkInTime(), r.checkOutTime()).toMinutes();
            if (minutes < 0) throw new BadRequestException("Check-out time must not be before check-in time");
            hours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        }
        a.setEmployee(employee); a.setAttendanceDate(r.attendanceDate()); a.setAttendanceStatus(r.attendanceStatus());
        a.setCheckInTime(r.checkInTime()); a.setCheckOutTime(r.checkOutTime()); a.setTotalWorkingHours(hours); a.setRemarks(r.remarks()); return a;
    }
}
