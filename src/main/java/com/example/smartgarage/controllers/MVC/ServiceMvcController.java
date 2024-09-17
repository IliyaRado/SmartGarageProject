package com.example.smartgarage.controllers.MVC;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/services")
public class ServiceMvcController {

    @GetMapping
    public String showServices() {
        return "services";
    }

}




