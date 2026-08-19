package com.example.app.controller;

import com.example.app.dto.*;
import com.example.app.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@Tag(name="Attendance")
public class AttendanceController {
    private final AttendanceService service;
    public AttendanceController(AttendanceService service) { this.service=service; }
    @PostMapping @Operation(summary="Mark employee attendance")
    public ResponseEntity<AttendanceResponse> create(@Valid @RequestBody AttendanceRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request)); }
    @GetMapping("/{id}") @Operation(summary="Get an attendance record")
    public ResponseEntity<AttendanceResponse> get(@PathVariable Long id) { return ResponseEntity.ok(service.get(id)); }
    @GetMapping @Operation(summary="Get all or search attendance records by employee ID, name, and date")
    public ResponseEntity<List<AttendanceResponse>> search(@RequestParam(required=false) Long employeeId,
        @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam(required=false) String employeeName, @RequestParam(required=false) Long afterId,
        @RequestParam(defaultValue="20") int limit) { return ResponseEntity.ok(service.search(employeeId,date,employeeName,afterId,limit)); }
    @GetMapping("/employee/{employeeId}") @Operation(summary="Get attendance records for a specific employee")
    public ResponseEntity<List<AttendanceResponse>> byEmployee(@PathVariable Long employeeId, @RequestParam(required=false) Long afterId,
        @RequestParam(defaultValue="20") int limit) { return ResponseEntity.ok(service.search(employeeId,null,null,afterId,limit)); }
    @GetMapping("/date/{date}") @Operation(summary="Get attendance records for a specific date")
    public ResponseEntity<List<AttendanceResponse>> byDate(@PathVariable @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam(required=false) Long afterId, @RequestParam(defaultValue="20") int limit) { return ResponseEntity.ok(service.search(null,date,null,afterId,limit)); }
    @PutMapping("/{id}") @Operation(summary="Update or correct an attendance record")
    public ResponseEntity<AttendanceResponse> update(@PathVariable Long id, @Valid @RequestBody AttendanceRequest request) { return ResponseEntity.ok(service.update(id,request)); }
    @DeleteMapping("/{id}") @Operation(summary="Delete an attendance record")
    public ResponseEntity<ApiMessage> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok(new ApiMessage("Attendance record deleted successfully")); }
}
