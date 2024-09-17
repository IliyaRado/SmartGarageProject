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

    @GetMapping("/login")
    public String login(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            return "redirect:/";
        }
        model.addAttribute("login", new LoginDto());
        return "login";
    }


    @PostMapping("/login")
    public String loginPost(@ModelAttribute("login") LoginDto loginDto, Model model) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("loginError", "Invalid username or password.");
            return "login";
        }
    }

}
