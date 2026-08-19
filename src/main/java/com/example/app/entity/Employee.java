package com.example.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees", uniqueConstraints = @UniqueConstraint(name = "uk_employee_email", columnNames = "email"))
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 80) private String firstName;
    @Column(nullable = false, length = 80) private String lastName;
    @Column(nullable = false, length = 160) private String email;
    @Column(nullable = false, length = 30) private String phoneNumber;
    @Column(nullable = false, length = 100) private String department;
    @Column(nullable = false, length = 100) private String designation;
    @Column(nullable = false) private LocalDate joiningDate;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private EmploymentStatus employmentStatus;
    @JsonIgnore @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendanceRecords = new ArrayList<>();

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; } public void setFirstName(String v) { firstName = v; }
    public String getLastName() { return lastName; } public void setLastName(String v) { lastName = v; }
    public String getEmail() { return email; } public void setEmail(String v) { email = v; }
    public String getPhoneNumber() { return phoneNumber; } public void setPhoneNumber(String v) { phoneNumber = v; }
    public String getDepartment() { return department; } public void setDepartment(String v) { department = v; }
    public String getDesignation() { return designation; } public void setDesignation(String v) { designation = v; }
    public LocalDate getJoiningDate() { return joiningDate; } public void setJoiningDate(LocalDate v) { joiningDate = v; }
    public EmploymentStatus getEmploymentStatus() { return employmentStatus; } public void setEmploymentStatus(EmploymentStatus v) { employmentStatus = v; }
    public List<Attendance> getAttendanceRecords() { return attendanceRecords; }
}
