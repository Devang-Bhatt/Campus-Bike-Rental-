package com.campusbikeapp.controller;

import com.campusbikeapp.service.ApiClient;
import com.campusbikeapp.session.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Web equivalent of Android QRScanActivity.java
 * Handles manual bike ID entry and bike unlock
 */
@Controller
@RequestMapping("/scan")
public class QRScanController {

    private final SessionManager sessionManager;
    private final ApiClient apiClient;

    public QRScanController(SessionManager sessionManager, ApiClient apiClient) {
        this.sessionManager = sessionManager;
        this.apiClient = apiClient;
    }

    @GetMapping
    public String scanPage(Model model) {
        if (!sessionManager.isLoggedIn()) return "redirect:/login";
        model.addAttribute("walletBalance", String.format("%.2f", sessionManager.getWalletBalance()));
        model.addAttribute("studentId", sessionManager.getStudentId());
        model.addAttribute("userName", sessionManager.getFullName());
        return "scan";
    }

    /**
     * Same as Android QRScanActivity.handleBikeScanned()
     * POST when user confirms a bike ID (manually typed or from QR)
     */
    @PostMapping("/unlock")
    public String unlockBike(@RequestParam String bikeId,
                             RedirectAttributes ra) {
        if (!sessionManager.isLoggedIn()) return "redirect:/login";

        if (bikeId == null || bikeId.isBlank()) {
            ra.addFlashAttribute("error", "Please enter a bike ID");
            return "redirect:/scan";
        }

        try {
            // TODO: Call Spring Boot backend to unlock bike:
            // String response = apiClient.postAuth(
            //     "/api/bikes/" + bikeId + "/unlock", null, sessionManager.getToken());

            // Same behavior as Android: show success + go to active ride
            ra.addFlashAttribute("bikeId", bikeId);
            ra.addFlashAttribute("success", "Bike " + bikeId + " unlocked successfully! 🚲");
            return "redirect:/ride/start?bikeId=" + bikeId;

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Failed to unlock bike: " + e.getMessage());
            return "redirect:/scan";
        }
    }
}
