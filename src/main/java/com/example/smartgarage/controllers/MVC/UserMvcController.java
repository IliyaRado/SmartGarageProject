package com.example.smartgarage.controllers.MVC;

import com.example.smartgarage.models.User;
import com.example.smartgarage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserMvcController extends BaseController{

    private final UserService userService;

    public UserMvcController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
}
