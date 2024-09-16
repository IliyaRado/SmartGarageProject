package com.example.smartgarage.controllers.MVC;

import com.example.smartgarage.models.User;
import com.example.smartgarage.models.dtos.RegisterDto;
import com.example.smartgarage.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

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
            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "There was an error during registration. Please try again.");
            return "register";
        }
    }
}
