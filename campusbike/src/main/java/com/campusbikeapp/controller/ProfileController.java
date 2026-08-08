package com.campusbikeapp.controller;

import com.campusbikeapp.service.RideHistoryService;
import com.campusbikeapp.session.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Web equivalent of Android ProfileActivity.java
 * Handles wallet display, Add Money, and Ride History navigation
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final SessionManager sessionManager;
    private final RideHistoryService rideHistoryService;

    public ProfileController(SessionManager sessionManager,
                              RideHistoryService rideHistoryService) {
        this.sessionManager = sessionManager;
        this.rideHistoryService = rideHistoryService;
    }

    @GetMapping
    public String profilePage(Model model) {
        if (!sessionManager.isLoggedIn()) return "redirect:/login";

        model.addAttribute("userName", sessionManager.getFullName());
        model.addAttribute("studentId", sessionManager.getStudentId());
        model.addAttribute("email", sessionManager.getEmail());
        model.addAttribute("walletBalance", String.format("%.2f", sessionManager.getWalletBalance()));
        model.addAttribute("recentRides",
            rideHistoryService.getRideHistory(sessionManager.getToken()).subList(0, 2));
        return "profile";
    }

    /**
     * Same as Android ProfileActivity.payUsingUpi() flow
     * In web: we just update balance directly (UPI deep-link not possible in browser)
     */
    @PostMapping("/add-money")
    public String addMoney(@RequestParam double amount, RedirectAttributes ra) {
        if (!sessionManager.isLoggedIn()) return "redirect:/login";

        if (amount < 10) {
            ra.addFlashAttribute("error", "Minimum top-up amount is Rs. 10");
            return "redirect:/profile";
        }

        // TODO: Initiate real UPI/payment gateway and verify callback
        sessionManager.addToWallet(amount);
        ra.addFlashAttribute("success",
            String.format("Rs. %.0f added to your wallet! 💰", amount));
        return "redirect:/profile";
    }
}
