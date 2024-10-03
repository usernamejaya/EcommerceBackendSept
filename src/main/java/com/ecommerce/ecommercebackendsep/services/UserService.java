package com.ecommerce.ecommercebackendsep.services;

import com.ecommerce.ecommercebackendsep.Exceptions.InvalidTokenException;
import com.ecommerce.ecommercebackendsep.Exceptions.UserDoesNotExistException;
import com.ecommerce.ecommercebackendsep.dtos.LoginRequestDTO;
import com.ecommerce.ecommercebackendsep.dtos.UserDTO;
import com.ecommerce.ecommercebackendsep.models.Token;
import com.ecommerce.ecommercebackendsep.models.User;
import com.ecommerce.ecommercebackendsep.repositories.TokenRepository;
import com.ecommerce.ecommercebackendsep.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public UserService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder
            ,TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public UserDTO register(User user){
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        User savedUser = userRepository.save(newUser);
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(savedUser.getEmail());
        return userDTO;
    }

    public Token login(LoginRequestDTO loginRequestDTO) throws UserDoesNotExistException {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequestDTO.getEmail());
        if(optionalUser.isEmpty()){
            throw new UserDoesNotExistException("User with email doesn't exits");

        }
        User user = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            throw new UserDoesNotExistException("Wrong password");
        }
        LocalDateTime currentDate = LocalDateTime.now();

        // Add 30 days to the current date
        LocalDateTime expiryDate = currentDate.plusDays(30);

        //since i'm storing as date in token ,
        //covert localDateTime to Date

        Date date = Date.from(expiryDate.atZone(ZoneId.systemDefault()).toInstant());
        Token token = new Token();
        token.setExpiryAt(date);
        token.setUser(user);
        token.setValue("RandomString"+user.getUsername());
        Token savedToken =tokenRepository.save(token);
        return savedToken;

    }


    public void logout(String token) throws InvalidTokenException {
        Optional<Token> optionalToken =tokenRepository.findByValueAndDeletedEquals(token,false);
        if(optionalToken.isEmpty()){
            throw new InvalidTokenException("invalid token");
        }
        Token t = optionalToken.get();
        t.setDeleted(true);
        tokenRepository.save(t);
        return;

    }

    public User validate(String token) throws InvalidTokenException {
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedEqualsAndExpiryAtGreaterThan(token, false, new Date());
        if (optionalToken.isEmpty()) {
            throw new InvalidTokenException("token is invalid or expired");
        }
        User user = optionalToken.get().getUser();
        return user;
    }
}
