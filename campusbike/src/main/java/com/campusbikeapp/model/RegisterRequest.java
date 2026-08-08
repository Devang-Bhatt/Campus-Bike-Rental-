package com.campusbikeapp.model;

public class RegisterRequest {
    private String fullName;
    private String studentId;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;

    public RegisterRequest() {}

    public String getFullName() { return fullName; }
    public void setFullName(String v) { this.fullName = v; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String v) { this.studentId = v; }

    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }

    public String getPhone() { return phone; }
    public void setPhone(String v) { this.phone = v; }

    public String getPassword() { return password; }
    public void setPassword(String v) { this.password = v; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String v) { this.confirmPassword = v; }
}
