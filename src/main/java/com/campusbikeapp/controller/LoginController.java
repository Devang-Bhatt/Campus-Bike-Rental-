package com.campusbikeapp.controller;

import com.campusbikeapp.model.LoginRequest;
import com.campusbikeapp.model.RegisterRequest;
import com.campusbikeapp.service.ApiClient;
import com.campusbikeapp.session.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Web equivalent of Android LoginActivity.java + RegisterActivity.java
 */
@Controller
public class LoginController {

    private final SessionManager sessionManager;
    private final ApiClient apiClient;

    public LoginController(SessionManager sessionManager, ApiClient apiClient) {
        this.sessionManager = sessionManager;
        this.apiClient = apiClient;
    }

    /* ── GET /login ── */
    @GetMapping("/login")
    public String loginPage(Model model) {
        if (sessionManager.isLoggedIn()) return "redirect:/home";
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    /* ── GET / ── redirect */
    @GetMapping("/")
    public String root() {
        return sessionManager.isLoggedIn() ? "redirect:/home" : "redirect:/login";
    }

    /* ── POST /login ── same validation as Android LoginActivity.validateInputs() */
    @PostMapping("/login")
    public String doLogin(@ModelAttribute LoginRequest req,
                          RedirectAttributes ra) {

        // Same validation as Android LoginActivity.java
        if (req.getStudentId() == null || req.getStudentId().isBlank()) {
            ra.addFlashAttribute("error", "Student ID is required");
            return "redirect:/login";
        }
        if (req.getEmail() == null || req.getEmail().isBlank()) {
            ra.addFlashAttribute("error", "Email is required");
            return "redirect:/login";
        }
        if (!req.getEmail().contains("@")) {
            ra.addFlashAttribute("error", "Enter a valid email address");
            return "redirect:/login";
        }
        if (req.getPassword() == null || req.getPassword().length() < 6) {
            ra.addFlashAttribute("error", "Password must be at least 6 characters");
            return "redirect:/login";
        }

        try {
            // TODO: Uncomment when Spring Boot backend is ready:
            // String response = apiClient.post("/api/auth/login", req);
            // JsonObject json = apiClient.getGson().fromJson(response, JsonObject.class);
            // String token = json.get("token").getAsString();
            // sessionManager.saveSession(token, req.getStudentId(), req.getEmail(), "Campus Student");

            // Simulated login (same as Android sample_token_123)
            sessionManager.saveSession(
                "sample_token_123",
                req.getStudentId(),
                req.getEmail(),
                "Campus Student"
            );
            ra.addFlashAttribute("success", "Login successful! Welcome back 🚲");
            return "redirect:/home";

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Login failed: " + e.getMessage());
            return "redirect:/login";
        }
    }

    /* ── GET /register ── */
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    /* ── POST /register ── same validation as Android RegisterActivity.validateInputs() */
    @PostMapping("/register")
    public String doRegister(@ModelAttribute RegisterRequest req,
                             RedirectAttributes ra) {

        if (req.getFullName() == null || req.getFullName().isBlank()) {
            ra.addFlashAttribute("error", "Full name is required");
            return "redirect:/register";
        }
        if (req.getStudentId() == null || req.getStudentId().isBlank()) {
            ra.addFlashAttribute("error", "Student ID is required");
            return "redirect:/register";
        }
        if (req.getEmail() == null || !req.getEmail().contains("@")) {
            ra.addFlashAttribute("error", "Enter a valid email");
            return "redirect:/register";
        }
        if (req.getPhone() == null || req.getPhone().length() < 10) {
            ra.addFlashAttribute("error", "Enter a valid phone number (min 10 digits)");
            return "redirect:/register";
        }
        if (req.getPassword() == null || req.getPassword().length() < 6) {
            ra.addFlashAttribute("error", "Password must be at least 6 characters");
            return "redirect:/register";
        }
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            ra.addFlashAttribute("error", "Passwords do not match");
            return "redirect:/register";
        }

        try {
            // TODO: Call real API:
            // apiClient.post("/api/auth/register", req);

            ra.addFlashAttribute("success", "Account created successfully! Please login.");
            return "redirect:/login";

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
    }

    /* ── POST /logout ── same as Android ProfileActivity logout button */
    @PostMapping("/logout")
    public String doLogout(RedirectAttributes ra) {
        sessionManager.clearSession();
        ra.addFlashAttribute("success", "Logged out successfully.");
        return "redirect:/login";
    }
}
