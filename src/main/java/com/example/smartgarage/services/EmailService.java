package com.example.smartgarage.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    // Method to send an email with an HTML template
    public void sendEmailWithTemplate(String to, String subject, String templatePath, Map<String, Object> templateModel) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Set context for the template with the passed model variables
        Context context = new Context();
        context.setVariables(templateModel);

        // Process the Thymeleaf template into an HTML string
        String htmlContent = templateEngine.process(templatePath, context);

        // Configure email properties
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);  // true to indicate it's HTML content
        helper.setFrom(fromAddress);

        // Send the email
        javaMailSender.send(message);
    }

    // Send password reset email
    public void sendPasswordResetEmail(String to, String token) {
        String subject = "Password Reset Request";
        String resetUrl = "http://localhost:8080/auth/reset-password?token=" + token;

        Map<String, Object> model = new HashMap<>();
        model.put("resetUrl", resetUrl);

        try {
            sendEmailWithTemplate(to, subject, "password-reset-email", model);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle the exception properly, maybe log it
        }
    }

    // Send welcome email
    public void sendWelcomeEmail(String to, String username, String password) {
        String subject = "Welcome to Vehicle Garage";

        Map<String, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("password", password);

        try {
            sendEmailWithTemplate(to, subject, "welcome-email", model);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle the exception properly, maybe log it
        }
    }
}
