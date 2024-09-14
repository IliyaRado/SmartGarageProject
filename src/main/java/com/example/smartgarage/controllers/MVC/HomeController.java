package com.example.smartgarage.controllers.MVC;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage() {

        return "index";
    }

    @GetMapping("/about")
    public String showAboutPage() {

        return "about";
    }
}
