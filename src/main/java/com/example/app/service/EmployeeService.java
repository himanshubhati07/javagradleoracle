package com.example.app.service;

import com.example.app.dto.*;
import com.example.app.entity.Employee;
import com.example.app.exception.*;
import com.example.app.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository repository;
    public EmployeeService(EmployeeRepository repository) { this.repository = repository; }
    public EmployeeResponse create(EmployeeRequest r) {
        if (repository.existsByEmailIgnoreCase(r.email())) throw new ConflictException("Employee email already exists");
        Employee e = apply(new Employee(), r);
        return EmployeeResponse.from(repository.save(e));
    }
    @Transactional(readOnly=true) public EmployeeResponse get(Long id) { return EmployeeResponse.from(find(id)); }
    @Transactional(readOnly=true) public List<EmployeeResponse> list(Long afterId, String name, int limit) {
        int safeLimit = Math.min(Math.max(limit, 1), 20);
        String q = name == null || name.isBlank() ? null : name.trim();
        return repository.search(afterId == null ? 0L : afterId, q, PageRequest.of(0, safeLimit)).stream().map(EmployeeResponse::from).toList();
    }
    public EmployeeResponse update(Long id, EmployeeRequest r) {
        if (repository.existsByEmailIgnoreCaseAndIdNot(r.email(), id)) throw new ConflictException("Employee email already exists");
        return EmployeeResponse.from(repository.save(apply(find(id), r)));
    }
    public void delete(Long id) { repository.delete(find(id)); }
    Employee find(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee " + id + " not found")); }
    private Employee apply(Employee e, EmployeeRequest r) {
        e.setFirstName(r.firstName().trim()); e.setLastName(r.lastName().trim()); e.setEmail(r.email().trim().toLowerCase());
        e.setPhoneNumber(r.phoneNumber().trim()); e.setDepartment(r.department().trim()); e.setDesignation(r.designation().trim());
        e.setJoiningDate(r.joiningDate()); e.setEmploymentStatus(r.employmentStatus()); return e;
    }
}
