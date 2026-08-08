package com.campusbikeapp.controller;

import com.campusbikeapp.service.ApiClient;
import com.campusbikeapp.session.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Web equivalent of Android ActiveRideActivity.java
 * Manages ride start, live timer (via JS), and end ride
 */

@Controller
@RequestMapping("/ride")
public class ActiveRideController {

    private final SessionManager sessionManager;
    private final ApiClient apiClient;

    public ActiveRideController(SessionManager sessionManager, ApiClient apiClient) {
        this.sessionManager = sessionManager;
        this.apiClient = apiClient;
    }

    @GetMapping("/start")
    public String startRide(@RequestParam String bikeId,
            HttpSession session,
            Model model) {
        if (!sessionManager.isLoggedIn())
            return "redirect:/login";

        // Store ride state in session (like Android stores in memory)
        session.setAttribute("active_bike_id", bikeId);
        session.setAttribute("ride_start_time", System.currentTimeMillis());

        // apiClient.postAuth("/api/rides/start", Map.of("bikeId", bikeId),
        // sessionManager.getToken());

        model.addAttribute("bikeId", bikeId);
        model.addAttribute("walletBalance", String.format("%.2f", sessionManager.getWalletBalance()));
        model.addAttribute("studentId", sessionManager.getStudentId());
        model.addAttribute("userName", sessionManager.getFullName());
        model.addAttribute("rideStartTime", session.getAttribute("ride_start_time"));
        return "active_ride";
    }

    @GetMapping
    public String ridePage(HttpSession session, Model model) {
        if (!sessionManager.isLoggedIn())
            return "redirect:/login";

        Object bikeId = session.getAttribute("active_bike_id");
        Object startTime = session.getAttribute("ride_start_time");

        if (bikeId == null)
            return "redirect:/scan";

        model.addAttribute("bikeId", bikeId);
        model.addAttribute("walletBalance", String.format("%.2f", sessionManager.getWalletBalance()));
        model.addAttribute("studentId", sessionManager.getStudentId());
        model.addAttribute("userName", sessionManager.getFullName());
        model.addAttribute("rideStartTime", startTime);
        return "active_ride";
    }

    /**
     * Same as Android ActiveRideActivity.endRide()
     * Deducts cost from wallet
     */
    @PostMapping("/end")
    public String endRide(@RequestParam(defaultValue = "5") double cost,
            HttpSession session,
            RedirectAttributes ra) {
        if (!sessionManager.isLoggedIn())
            return "redirect:/login";

        // Deduct cost (same logic as Android)
        sessionManager.deductFromWallet(cost);
        session.removeAttribute("active_bike_id");
        session.removeAttribute("ride_start_time");

        // TODO: Call Spring Boot backend to end ride:
        // apiClient.postAuth("/api/rides/end", Map.of("cost", cost),
        // sessionManager.getToken());

        ra.addFlashAttribute("success",
                String.format("Ride ended! Rs. %.0f charged. Safe riding! 🚲", cost));
        return "redirect:/home";
    }
}
