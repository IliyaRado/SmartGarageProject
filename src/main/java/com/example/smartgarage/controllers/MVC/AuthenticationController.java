package com.example.smartgarage.controllers.MVC;

import com.example.smartgarage.exceptions.DuplicateEntityException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.User;
import com.example.smartgarage.models.dtos.RegisterDto;
import com.example.smartgarage.services.EmailService;
import com.example.smartgarage.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;
    private EmailService emailService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerDto") RegisterDto registerDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {

            return "register";
        }

        try {

            User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setPhoneNumber(registerDto.getPhoneNumber());
            user.setUsername(registerDto.getUsername());
            userService.createUser(user);

            model.addAttribute("successMessage", "Registration successful! Please check your email for login details.");
            return "redirect:/appointment";
        } catch (DuplicateEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "There was an error during registration. Please try again.");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/request-reset")
    public String showPasswordResetRequestForm(Model model) {
        return "password-reset-request";
    }

    @PostMapping("/request-reset")
    public String handlePasswordResetRequest(@RequestParam("email") String email, Model model) {
        try {
            userService.sendPasswordResetLink(email);
            model.addAttribute("successMessage", "Password reset email sent! Please check your inbox.");
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "Email not found.");
        }
        return "password-reset-request";
    }

    @GetMapping("/reset-password")
    public String showPasswordResetForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "password-reset";
    }

    @PostMapping("/reset-password")
    public String handlePasswordReset(@RequestParam("token") String token,
                                      @RequestParam("password") String newPassword,
                                      Model model) {
        try {
            userService.resetPassword(token, newPassword);
            model.addAttribute("successMessage", "Password successfully reset!");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Invalid token or expired link.");
            return "password-reset";
        }
    }

}
