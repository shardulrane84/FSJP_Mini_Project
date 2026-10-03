package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("roles", User.Role.values());
        return "signup"; // templates/signup.html
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam User.Role role,
            Model model) {
        try {
            userService.signup(name, email, password, role);
            return "redirect:/login?signupSuccess=true";
        } catch (EmailAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", User.Role.values());
            return "signup";
        }
    }

    // Sends the logged-in user to the correct dashboard based on their role
    @GetMapping("/dashboard-redirect")
    public String dashboardRedirect(Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        return switch (role) {
            case "ROLE_ADMIN" -> "redirect:/admin/dashboard";
            case "ROLE_SPEAKER" -> "redirect:/speaker/dashboard";
            case "ROLE_STUDENT" -> "redirect:/student/dashboard";
            default -> "redirect:/login";
        };
    }
}