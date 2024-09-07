package com.example.smartgarage.config;

import com.example.smartgarage.services.UserService;
import com.example.smartgarage.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public static final int BCRYPT_STRENGTH = 11;

    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/myCards").authenticated()
                        .requestMatchers("/api/users/**").permitAll()
                        .requestMatchers("/api/users").permitAll()
                        .requestMatchers("/api/vehicles/**").permitAll()
                        .requestMatchers("/api/vehicles").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/vehicles/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/api/vehicles/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE, "/api/vehicles/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )

                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH);
    }

}
