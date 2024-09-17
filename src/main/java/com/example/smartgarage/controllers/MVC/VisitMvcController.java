package com.example.smartgarage.controllers.MVC;

import com.example.smartgarage.models.Service;
import com.example.smartgarage.models.User;
import com.example.smartgarage.models.Visit;
import com.example.smartgarage.models.dtos.AppointmentDto;
import com.example.smartgarage.models.dtos.VisitReportDto;
import com.example.smartgarage.repositories.ModelRepository;
import com.example.smartgarage.services.EmailService;
import com.example.smartgarage.services.ServiceService;
import com.example.smartgarage.services.VehicleService;
import com.example.smartgarage.services.VisitService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VisitMvcController {

    private final VehicleService vehicleService;
    private final ModelRepository modelRepository;
    private final ServiceService serviceService;
    private final VisitService visitService;
    private final EmailService emailService;


    @Autowired
    public VisitMvcController(VehicleService vehicleService, ModelRepository modelRepository, ServiceService serviceService, VisitService visitService, EmailService emailService) {
        this.vehicleService = vehicleService;
        this.modelRepository = modelRepository;
        this.serviceService = serviceService;
        this.visitService = visitService;
        this.emailService = emailService;
    }

    @GetMapping
    @RequestMapping("/appointment")
    public String showAppointmentForm(Model model) {
        List<com.example.smartgarage.models.Model> carModels = modelRepository.findAll();
        List<Service> services = serviceService.getAll();

        model.addAttribute("carModels", carModels);
        model.addAttribute("services", services);

        model.addAttribute("visit", new AppointmentDto());

        return "appointment";
    }


    @PostMapping("/appointment/submit")
    public String submitAppointmentForm(@Valid @ModelAttribute("visit") AppointmentDto appointmentDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            List<com.example.smartgarage.models.Model> carModels = modelRepository.findAll();
            List<Service> services = serviceService.getAll();

            model.addAttribute("carModels", carModels);
            model.addAttribute("services", services);
            return "appointment";
        }
        Visit visit = visitService.createVisit(appointmentDto);
        return "redirect:/visit/report/" + visit.getId();
    }


    @GetMapping("/visit/report/{id}")
    public String showVisitReport(@PathVariable("id") int visitId, Model model) {

        VisitReportDto reportDto = visitService.generateVisitReport(visitId, "BGN");
        model.addAttribute("report", reportDto);
        model.addAttribute("currency", "BGN");

        return "visit-report";
    }

    @PostMapping("/visit/report/currency")
    public String selectCurrency(@RequestParam("visitId") int visitId, @RequestParam("currency") String currency, Model model) {
        VisitReportDto reportDto = visitService.generateVisitReport(visitId, currency); // Convert prices
        model.addAttribute("report", reportDto);

        return "/visit/report/" + visitId;
    }

    @PostMapping("/visit/report/email")
    public String sendVisitReportByEmail(@RequestParam("id") int visitId) {

        VisitReportDto report = visitService.generateVisitReport(visitId, "BGN"); // Or whichever default currency
        User user = report.getCustomer();

        Map<String, Object> model = new HashMap<>();
        model.put("username", user.getUsername());
        model.put("email", user.getEmail());
        model.put("vehicleModel", report.getVehicle().getModel().getName());
        model.put("vehicleYear", report.getVehicle().getYearOfCreation());
        model.put("licensePlate", report.getVehicle().getLicensePlate());
        model.put("services", report.getServices());
        model.put("totalPrice", report.getTotalPrice());
        model.put("currencyCode", report.getCurrencyCode());


        try {
            emailService.sendEmailWithTemplate(user.getEmail(), "Visit Report", "visit-report-email", model);
        } catch (MessagingException e) {

            e.printStackTrace();
        }

        return "redirect:/";
    }
}
