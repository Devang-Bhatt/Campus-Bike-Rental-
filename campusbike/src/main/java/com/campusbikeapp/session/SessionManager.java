package com.campusbikeapp.session;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Web equivalent of Android SessionManager.java
 * Uses HttpSession instead of SharedPreferences
 */
@Component
@SessionScope
public class SessionManager {

    private static final String KEY_TOKEN      = "jwt_token";
    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_EMAIL      = "email";
    private static final String KEY_NAME       = "full_name";
    private static final String KEY_LOGGED_IN  = "is_logged_in";
    private static final String KEY_WALLET     = "wallet_balance";

    private final HttpSession session;

    public SessionManager(HttpSession session) {
        this.session = session;
    }

    public void saveSession(String token, String studentId, String email, String name) {
        session.setAttribute(KEY_TOKEN, token);
        session.setAttribute(KEY_STUDENT_ID, studentId);
        session.setAttribute(KEY_EMAIL, email);
        session.setAttribute(KEY_NAME, name);
        session.setAttribute(KEY_LOGGED_IN, true);
        session.setAttribute(KEY_WALLET, 100.0);
    }

    public String getToken() {
        Object v = session.getAttribute(KEY_TOKEN);
        return v != null ? v.toString() : null;
    }

    public String getStudentId() {
        Object v = session.getAttribute(KEY_STUDENT_ID);
        return v != null ? v.toString() : null;
    }

    public String getEmail() {
        Object v = session.getAttribute(KEY_EMAIL);
        return v != null ? v.toString() : null;
    }

    public String getFullName() {
        Object v = session.getAttribute(KEY_NAME);
        return v != null ? v.toString() : "Campus Student";
    }

    public boolean isLoggedIn() {
        Object v = session.getAttribute(KEY_LOGGED_IN);
        return Boolean.TRUE.equals(v);
    }

    public double getWalletBalance() {
        Object v = session.getAttribute(KEY_WALLET);
        return v != null ? (double) v : 100.0;
    }

    public void setWalletBalance(double balance) {
        session.setAttribute(KEY_WALLET, balance);
    }

    public void addToWallet(double amount) {
        setWalletBalance(getWalletBalance() + amount);
    }

    public void deductFromWallet(double amount) {
        setWalletBalance(Math.max(0, getWalletBalance() - amount));
    }

    public void clearSession() {
        session.invalidate();
    }
}
