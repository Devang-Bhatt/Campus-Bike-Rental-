package com.campusbikeapp.controller;

import com.campusbikeapp.service.RideHistoryService;
import com.campusbikeapp.session.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Web equivalent of Android RideHistoryActivity.java
 */
@Controller
@RequestMapping("/history")
public class RideHistoryController {

    private final SessionManager sessionManager;
    private final RideHistoryService rideHistoryService;

    public RideHistoryController(SessionManager sessionManager,
                                  RideHistoryService rideHistoryService) {
        this.sessionManager = sessionManager;
        this.rideHistoryService = rideHistoryService;
    }

    @GetMapping
    public String historyPage(Model model) {
        if (!sessionManager.isLoggedIn()) return "redirect:/login";

        model.addAttribute("rides", rideHistoryService.getRideHistory(sessionManager.getToken()));
        model.addAttribute("walletBalance", String.format("%.2f", sessionManager.getWalletBalance()));
        model.addAttribute("studentId", sessionManager.getStudentId());
        model.addAttribute("userName", sessionManager.getFullName());
        return "history";
    }
}
