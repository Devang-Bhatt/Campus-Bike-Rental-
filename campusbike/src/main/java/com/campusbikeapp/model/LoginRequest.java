package com.campusbikeapp.model;

public class LoginRequest {
    private String studentId;
    private String email;
    private String password;

    public LoginRequest() {}
    public LoginRequest(String studentId, String email, String password) {
        this.studentId = studentId;
        this.email = email;
        this.password = password;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String v) { this.studentId = v; }

    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }

    public String getPassword() { return password; }
    public void setPassword(String v) { this.password = v; }
}
