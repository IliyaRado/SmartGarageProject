package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.DuplicateEntityException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.PasswordGenerator;
import com.example.smartgarage.models.PasswordResetToken;
import com.example.smartgarage.models.Role;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.PasswordResetTokenRepository;
import com.example.smartgarage.repositories.RoleRepository;
import com.example.smartgarage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.util.StringUtils;

import java.util.*;

import static com.example.smartgarage.filters.UserSpecifications.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PasswordGenerator passwordGenerator;
    private final PasswordResetTokenRepository tokenRepository;

    @Lazy
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                           EmailService emailService, PasswordGenerator passwordGenerator, PasswordResetTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.passwordGenerator = passwordGenerator;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(String usernameFilter, String emailFilter, String phoneNumberFilter, Pageable pageable) {
        Specification<User> filters = Specification
                .where(StringUtils.isEmptyOrWhitespace(usernameFilter) ? null : username(usernameFilter))
                .and(StringUtils.isEmptyOrWhitespace(emailFilter) ? null : email(emailFilter))
                .and(StringUtils.isEmptyOrWhitespace(phoneNumberFilter) ? null : phoneNumber(phoneNumberFilter));
        return userRepository.findAll(filters, pageable);
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User", "email", email));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User", "username", username));
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new EntityNotFoundException("User", "phone number", phoneNumber));
    }

    @Override
    public User createUser(User user) {
        checkUsernameUnique(user);
        checkEmailUnique(user);
        String generatedPassword = passwordGenerator.generatePassword();
        String encodedPassword = passwordEncoder.encode(generatedPassword);
        user.setPassword(encodedPassword);

        Role role = roleRepository.findByType("CUSTOMER").orElseThrow(() -> new EntityNotFoundException("Role", "type", "CUSTOMER"));
        user.setRole(role);
        User savedUser = userRepository.save(user);

        emailService.sendWelcomeEmail(
                user.getEmail(),
                user.getUsername(),
                generatedPassword
        );

        return savedUser;
    }

    @Override
    public User createEmployee(User user) {
        checkUsernameUnique(user);
        checkEmailUnique(user);
        Role role = roleRepository.findByType("EMPLOYEE").orElseThrow(() -> new EntityNotFoundException("Role", "type","EMPLOYEE"));
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("User", "username", authentication.getName()));

        User userToUpdate = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User", user.getId()));

        if (currentUser.getId() != user.getId()) {
            throw new AuthorizationException("You are not authorized to update this user.");
        }

        if (!currentUser.getEmail().equals(user.getEmail()) || !currentUser.getUsername().equals(user.getUsername())) {
            checkEmailUnique(user);
            checkUsernameUnique(user);
        }

        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setUsername(user.getUsername());

        return userRepository.save(userToUpdate);
    }

    @Override
    public void delete(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        userRepository.deleteById(id);
    }

    private void checkEmailUnique(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEntityException("User", "email", user.getEmail());
        }
    }

    private void checkUsernameUnique(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return List.of(new SimpleGrantedAuthority(user.getRole().getType()));
    }

    @Override
    public void sendPasswordResetLink(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User", "email", email));
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600 * 1000));  // 1 hour expiry
        tokenRepository.save(resetToken);

        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken == null || resetToken.getExpiryDate().before(new Date())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired token.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(resetToken);
    }
}