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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmailService emailService, PasswordGenerator passwordGenerator, PasswordResetTokenRepository tokenRepository) {
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

        User user = userRepository.findById(id);
        if (user == null){
            throw new EntityNotFoundException("User", id);
        }
        return user;

    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User", "email", email);
        }
        return user.orElse(null);
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User", "username", username);
        }
        return user;
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new EntityNotFoundException("User", "phone number", phoneNumber);
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        checkUsernameUnique(user);
        checkEmailUnique(user);
        String generatedPassword = passwordGenerator.generatePassword();
        String encodedPassword = passwordEncoder.encode(generatedPassword);
        user.setPassword(encodedPassword);
        Role role = roleRepository.findByType("CUSTOMER");
        user.setRole(role);
        User savedUser = userRepository.save(user);

        emailService.sendEmail(
                user.getEmail(),
                "Welcome to SmartGarage",
                String.format(
                        "Your username is: %s\nYour password is: %s\nRemember to keep your Username and Password secure.",
                        user.getUsername(), generatedPassword
                )
        );

        return savedUser;
    }

    @Override
    public User createEmployee(User user) {
        checkUsernameUnique(user);
        checkEmailUnique(user);
        Role role = roleRepository.findByType("EMPLOYEE");
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName());

        User userToUpdate = userRepository.findById(user.getId());
        if (userToUpdate == null) {
            throw new EntityNotFoundException("User", user.getId());
        }

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

        return userRepository.save(user);
    }

    @Override
    public void delete(int id) {

        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("User", id);
        }
        userRepository.deleteById(id);

    }

    private void checkEmailUnique(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new DuplicateEntityException("User", "email", user.getEmail());
        }
    }

    private void checkUsernameUnique(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return List.of(new SimpleGrantedAuthority(user.getRole().getType()));
    }

    public void sendPasswordResetLink(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User", "email", email));
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600 * 1000));
        tokenRepository.save(resetToken);
        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }
}