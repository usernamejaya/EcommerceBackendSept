package com.ecommerce.ecommercebackendsep.controllers;

import com.ecommerce.ecommercebackendsep.Exceptions.InvalidTokenException;
import com.ecommerce.ecommercebackendsep.Exceptions.UserDoesNotExistException;
import com.ecommerce.ecommercebackendsep.dtos.*;
import com.ecommerce.ecommercebackendsep.models.Token;
import com.ecommerce.ecommercebackendsep.models.User;
import com.ecommerce.ecommercebackendsep.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody SignupDTO signupDTO) {
        User user = new User();
        user.setUsername(signupDTO.getUsername());
        user.setPassword(signupDTO.getPassword());
        user.setEmail(signupDTO.getEmail());
        user.setName(signupDTO.getName());
        UserDTO userDTO =userService.register(user);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws UserDoesNotExistException {
       Token token = userService.login(loginRequestDTO);
       LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
       loginResponseDTO.setEmail(token.getUser().getEmail());
       loginResponseDTO.setExpiryAt(token.getExpiryAt());
       loginResponseDTO.setTokenValue(token.getValue());
       return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDTO logoutRequestDTO) throws InvalidTokenException {
        userService.logout(logoutRequestDTO.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public UserDTO validate (@PathVariable ("token") String token) throws InvalidTokenException {
        User user = userService.validate(token);
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        return userDTO;


    }


}
