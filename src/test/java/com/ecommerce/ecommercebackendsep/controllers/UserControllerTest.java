package com.ecommerce.ecommercebackendsep.controllers;

import com.ecommerce.ecommercebackendsep.Exceptions.InvalidTokenException;
import com.ecommerce.ecommercebackendsep.Exceptions.UserDoesNotExistException;
import com.ecommerce.ecommercebackendsep.dtos.*;
import com.ecommerce.ecommercebackendsep.models.Token;
import com.ecommerce.ecommercebackendsep.models.User;
import com.ecommerce.ecommercebackendsep.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setUsername("john_doe");
        signupDTO.setPassword("password123");
        signupDTO.setEmail("john.doe@example.com");
        signupDTO.setName("John Doe");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("john.doe@example.com");

        // Mock the service method
        when(
                userService.register(any(User.class))).thenReturn(userDTO);

        // Call the controller method
        ResponseEntity<UserDTO> response = userController.register(signupDTO);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
        verify(userService, times(1)).register(any(User.class));
    }

    @Test
   public void testLogin() throws UserDoesNotExistException {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("john.doe@example.com");
        loginRequestDTO.setPassword("password123");

        Token token = new Token();
        User user = new User();
        user.setEmail("john.doe@example.com");
        token.setUser(user);
        token.setValue("sample-token");


        // Mock the service method
        when(userService.login(any(LoginRequestDTO.class))).thenReturn(token);

        // Call the controller method
        ResponseEntity<LoginResponseDTO> response = userController.login(loginRequestDTO);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
        assertEquals("sample-token", response.getBody().getTokenValue());
        verify(userService, times(1)).login(any(LoginRequestDTO.class));
    }

    @Test
    public void testLogout() throws  InvalidTokenException {
        LogoutRequestDTO logoutRequestDTO = new LogoutRequestDTO();
        logoutRequestDTO.setToken("sample-token");

        // Mock the service method
        doNothing().when(userService).logout(anyString());

        // Call the controller method
        ResponseEntity<Void> response = userController.logout(logoutRequestDTO);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).logout("sample-token");
    }

    @Test
    public void testValidate() throws InvalidTokenException {
        String token = "sample-token";
        User user = new User();
        user.setEmail("john.doe@example.com");

        // Mock the service method
        when(userService.validate(anyString())).thenReturn(user);

        // Call the controller method
        UserDTO userDTO = userController.validate(token);

        // Assertions
        assertEquals("john.doe@example.com", userDTO.getEmail());
        verify(userService, times(1)).validate("sample-token");
    }
    }


