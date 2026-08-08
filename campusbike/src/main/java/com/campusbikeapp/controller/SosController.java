package com.campusbikeapp.controller;

import com.campusbikeapp.service.ApiClient;
import com.campusbikeapp.session.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Web equivalent of Android SosActivity.java
 */
@Controller
@RequestMapping("/sos")
public class SosController {

    private final SessionManager sessionManager;
    private final ApiClient apiClient;

    public SosController(SessionManager sessionManager, ApiClient apiClient) {
        this.sessionManager = sessionManager;
        this.apiClient = apiClient;
    }

    @GetMapping
    public String sosPage(Model model) {
        if (!sessionManager.isLoggedIn())
            return "redirect:/login";

        model.addAttribute("walletBalance", String.format("%.2f", sessionManager.getWalletBalance()));
        model.addAttribute("studentId", sessionManager.getStudentId());
        model.addAttribute("userName", sessionManager.getFullName());
        return "sos";
    }

    @PostMapping("/send")
    public String sendSos(RedirectAttributes ra) {
        if (!sessionManager.isLoggedIn())
            return "redirect:/login";

        try {

            ra.addFlashAttribute("sosSent", true);
            ra.addFlashAttribute("success",
                    " Emergency alert sent to campus security!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Failed to send SOS: " + e.getMessage());
        }

        return "redirect:/sos";
    }
}
