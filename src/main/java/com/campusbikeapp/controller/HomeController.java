package com.campusbikeapp.controller;

import com.campusbikeapp.model.BikeStation;
import com.campusbikeapp.session.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Web equivalent of Android HomeActivity.java
 * Provides map data, stats, and nearby station list
 */
@Controller
public class HomeController {

    private final SessionManager sessionManager;

    public HomeController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        if (!sessionManager.isLoggedIn()) return "redirect:/login";

        // Same dashboard data as Android HomeActivity.loadDashboardData()
        model.addAttribute("walletBalance", String.format("%.2f", sessionManager.getWalletBalance()));
        model.addAttribute("bikeCount", 12);
        model.addAttribute("stationCount", 4);
        model.addAttribute("nearestBike", "50m");
        model.addAttribute("studentId", sessionManager.getStudentId());
        model.addAttribute("userName", sessionManager.getFullName());
        model.addAttribute("email", sessionManager.getEmail());

        // Same station data as Android HomeActivity.addDockStations()
        model.addAttribute("stations", List.of(
            new BikeStation("Main Gate Station",    30.3165, 78.0322, 3, "50m"),
            new BikeStation("Library Station",      30.3180, 78.0335, 5, "280m"),
            new BikeStation("Hostel Station",       30.3150, 78.0305, 2, "450m"),
            new BikeStation("Sports Complex Station", 30.3190, 78.0320, 2, "720m")
        ));

        return "home";
    }
}
