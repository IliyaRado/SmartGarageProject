package com.example.smartgarage.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
    private String username;

    @Column(name = "password")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[+\\-*^]).{8,}$", message = "Password must contain at least 8 symbols, including a capital letter, digit, and special symbol")
    private String password;

    @Column(name = "email")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "phone_number")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @Column(name = "employee")
    private String employee;

    public User() {
    }

    public User(int id, String username, String password, String email,
                String phone, String role, String employee, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.employee = employee;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return employee;
    }

    public void setRole(String role) {
        this.employee = employee;
    }
}
