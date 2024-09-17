package com.example.smartgarage.controllers.MVC;



import com.example.smartgarage.models.dtos.LoginDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController extends BaseController{
    private final AuthenticationManager authenticationManager;

    public HomeController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/")
    public String showHomePage() {

        return "index";
    }

    @GetMapping("/about")
    public String showAboutPage() {

        return "about";
    }

    @GetMapping("/contact")
    public String showContactPage() {

        return "contact";
    }
    @GetMapping("/services")
    public String showServicesPage() {

        return "services";
    }
    @GetMapping("/service-detail")
    public String showSingleServicePage() {

        return "service-detail";
    }

    @GetMapping("/login")
    public String login(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            return "redirect:/"; // Redirect if already logged in
        }
        model.addAttribute("login", new LoginDto());
        return "login"; // Show login page if not authenticated
    }


    @PostMapping("/login")
    public String loginPost(@ModelAttribute("login") LoginDto loginDto, Model model) {
        try {
            // Create authentication token with username and password from the form
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(authToken);

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Redirect to home page (or dashboard) upon successful login
            return "redirect:/";
        } catch (Exception e) {
            // If authentication fails, return to the login page with an error message
            model.addAttribute("loginError", "Invalid username or password.");
            return "login";
        }
    }

}
