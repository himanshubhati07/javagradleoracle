package com.example.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_users", uniqueConstraints = @UniqueConstraint(name = "uk_app_user_email", columnNames = "email"))
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 100) private String name;
    @Column(nullable = false, length = 160) private String email;
    @Column(nullable = false, length = 255) private String password;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String v) { name = v; }
    public String getEmail() { return email; } public void setEmail(String v) { email = v; }
    public String getPassword() { return password; } public void setPassword(String v) { password = v; }
}
