package com.example.app.controller;

import com.example.app.dto.*;
import com.example.app.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name="Employees")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) { this.service=service; }
    @PostMapping @Operation(summary="Add an employee")
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request)); }
    @GetMapping("/{id}") @Operation(summary="Get employee details")
    public ResponseEntity<EmployeeResponse> get(@PathVariable Long id) { return ResponseEntity.ok(service.get(id)); }
    @GetMapping @Operation(summary="List or search employees with cursor pagination")
    public ResponseEntity<List<EmployeeResponse>> list(@RequestParam(required=false) Long afterId, @RequestParam(required=false) String name,
        @RequestParam(defaultValue="20") int limit) { return ResponseEntity.ok(service.list(afterId,name,limit)); }
    @PutMapping("/{id}") @Operation(summary="Update an employee")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest request) { return ResponseEntity.ok(service.update(id,request)); }
    @DeleteMapping("/{id}") @Operation(summary="Delete an employee")
    public ResponseEntity<ApiMessage> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok(new ApiMessage("Employee deleted successfully")); }
}
