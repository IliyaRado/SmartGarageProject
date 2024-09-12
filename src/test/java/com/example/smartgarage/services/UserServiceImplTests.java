
package com.example.smartgarage.services;
import com.example.smartgarage.exceptions.DuplicateEntityException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.PasswordGenerator;
import com.example.smartgarage.models.Role;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.RoleRepository;
import com.example.smartgarage.repositories.UserRepository;
import com.example.smartgarage.services.EmailService;
import com.example.smartgarage.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordGenerator passwordGenerator;

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;
    private Role mockRole;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("testUser");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password");
        mockRole = new Role();
        mockRole.setId(1);
        mockRole.setType("CUSTOMER");

        mockUser.setRole(mockRole);
    }

    @Test
    void createUser_Successful() {
        // Mock repository to return no user for email check (since we're creating a new user)
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Mock role retrieval to return a role
        when(roleRepository.findByType("CUSTOMER")).thenReturn(mockRole);

        // Mock password encoding to return a known encoded value
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Mock the repository save method to simulate saving the user with an ID
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User userToSave = invocation.getArgument(0);
            userToSave.setId(1); // Simulate saving with ID assignment
            return userToSave;
        });

        // Call the method to test
        User createdUser = userService.createUser(mockUser);

        // Verify and assert
        assertNotNull(createdUser);
        assertEquals("encodedPassword", createdUser.getPassword()); // This should pass now
        assertEquals(mockUser.getUsername(), createdUser.getUsername());
    }



    @Test
    void createUser_FailsWhenDuplicateEmail() {

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(mockUser));

        assertThrows(DuplicateEntityException.class, () -> userService.createUser(mockUser));
    }

    @Test
    void updateUser_Successful() {
        // Mock the authentication context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.setContext(securityContext);

        // Mock the repository behavior for username and ID lookup
        when(userRepository.findByUsername("testuser")).thenReturn(mockUser); // Simulate authenticated user
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(mockUser)); // Mock user lookup by ID

        // Mock the save method to return the updated user
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Call the update method
        User updatedUser = userService.update(mockUser);

        // Assertions
        assertNotNull(updatedUser);
        assertEquals(mockUser.getUsername(), updatedUser.getUsername());
    }


    @Test
    void findUserById_UserExists() {
        // Given
        when(userRepository.findById(anyInt())).thenReturn(mockUser);

        User user = userService.findUserById(1);

        assertNotNull(user);
        assertEquals(1, user.getId());
    }

    @Test
    void findUserById_UserNotFound() {

        when(userRepository.findById(anyInt())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(999));
    }

    // More tests can be added for delete, password reset, etc.
}
