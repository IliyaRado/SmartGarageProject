package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.DuplicateEntityException;
import com.example.smartgarage.helpers.PasswordGenerator;
import com.example.smartgarage.models.Role;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.RoleRepository;
import com.example.smartgarage.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordGenerator passwordGenerator; // Mock the PasswordGenerator

    @Mock
    private EmailService emailService; // Mock the EmailService

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("testuser");
        mockUser.setEmail("testuser@example.com");
        mockUser.setPassword("rawPassword");
    }

    @Test
    void createUser_Successful() {
        Role customerRole = new Role();
        customerRole.setId(1);
        customerRole.setType("CUSTOMER");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());  // No duplicate email
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());  // No duplicate username
        when(roleRepository.findByType(anyString())).thenReturn(Optional.of(customerRole));  // Return Optional.of(customerRole)
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");  // Mock password encoding
        when(passwordGenerator.generatePassword()).thenReturn("generatedPassword");  // Mock the password generator
        when(userRepository.save(any(User.class))).thenReturn(mockUser);  // Mock saving the user



        User createdUser = userService.createUser(mockUser);

        assertNotNull(createdUser);
        assertEquals("encodedPassword", createdUser.getPassword());
        assertEquals(customerRole, createdUser.getRole());
        verify(userRepository, times(1)).save(mockUser);

    }

    @Test
    void createUser_FailsWhenDuplicateEmail() {

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));

        assertThrows(DuplicateEntityException.class, () -> userService.createUser(mockUser));
    }

    @Test
    void updateUser_Successful() {

        User userToUpdate = new User();
        userToUpdate.setId(1);
        userToUpdate.setUsername("updatedUser");
        userToUpdate.setEmail("updateduser@example.com");
        userToUpdate.setPhoneNumber("123456789");

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(mockUser.getUsername());
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.save(any(User.class))).thenReturn(userToUpdate);

        User updatedUser = userService.update(userToUpdate);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertNotNull(savedUser);
        assertEquals("updatedUser", savedUser.getUsername());
        assertEquals("updateduser@example.com", savedUser.getEmail());
        assertEquals("123456789", savedUser.getPhoneNumber());
    }
}
