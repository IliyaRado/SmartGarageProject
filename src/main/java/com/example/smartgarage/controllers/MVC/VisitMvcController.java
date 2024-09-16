package com.example.smartgarage.controllers.MVC;

import com.example.smartgarage.models.Service;
import com.example.smartgarage.models.dtos.AppointmentDto;
import com.example.smartgarage.repositories.ModelRepository;
import com.example.smartgarage.services.ServiceService;
import com.example.smartgarage.services.VehicleService;
import com.example.smartgarage.services.VisitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/appointment")
public class VisitMvcController {

    private final VehicleService vehicleService;
    private final ModelRepository modelRepository;
    private final ServiceService serviceService;
    private final VisitService visitService;

    @Autowired
    public VisitMvcController(VehicleService vehicleService, ModelRepository modelRepository, ServiceService serviceService, VisitService visitService) {
        this.vehicleService = vehicleService;
        this.modelRepository = modelRepository;
        this.serviceService = serviceService;
        this.visitService = visitService;
    }

    // GET method to display the form
    @GetMapping
    public String showAppointmentForm(Model model) {
        // Load car models and services from the services
        List<com.example.smartgarage.models.Model> carModels = modelRepository.findAll();
        List<Service> services = serviceService.getAll();

        // Add data to the model
        model.addAttribute("carModels", carModels);
        model.addAttribute("services", services);

        // Initialize the AppointmentDto object
        model.addAttribute("visit", new AppointmentDto());

        // Return the name of the view (appointment.html)
        return "appointment";
    }

    // POST method to handle form submission
    @PostMapping("/submit")
    public String submitAppointmentForm(@Valid @ModelAttribute("visit") AppointmentDto appointmentDto, BindingResult bindingResult, Model model) {
        // If there are validation errors, return back to the form with errors
        if (bindingResult.hasErrors()) {
            // Reload car models and services to be displayed again
            List<com.example.smartgarage.models.Model> carModels = modelRepository.findAll();
            List<Service> services = serviceService.getAll();

            model.addAttribute("carModels", carModels);
            model.addAttribute("services", services);
            return "appointment";
        }
        visitService.createVisit(appointmentDto);
        return "redirect:/";
    }
}
