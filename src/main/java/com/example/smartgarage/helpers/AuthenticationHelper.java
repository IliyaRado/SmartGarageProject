package com.example.smartgarage.helpers;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticationHelper {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String INVALID_AUTHENTICATION_ERROR = "Invalid username or password.";

    private final UserService userService;

    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    /*public User tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        String userInfo = headers.getFirst(AUTHORIZATION_HEADER_NAME);
        String username = getUsername(userInfo);
        String password = getPassword(userInfo);
        return verifyAuthentication(username, password);
    }*/

    /*public User tryGetCurrentUser(HttpSession session) {
        String currentUsername = (String) session.getAttribute("currentUser");

        if (currentUsername == null) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userService.findUserByUsername(currentUsername);
    }*/

    /*public User verifyAuthentication(String username, String password) {
        try {
            User user = userService.findUserByUsername(username);
            if (!user.getPassword().equals(password)) {
                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
            }
//            if (!user.isActive() || user.isBlocked()) {
//                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
//            }
            return user;
        } catch (EntityNotFoundException e) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
    }*/

    public static Authentication checkAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }
        return auth;
    }

    private String getUsername(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(0, firstSpace);
    }

    private String getPassword(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(firstSpace + 1);
    }
}
