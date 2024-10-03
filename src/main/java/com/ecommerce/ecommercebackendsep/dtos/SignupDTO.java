package com.ecommerce.ecommercebackendsep.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    private String name;
    private String email;
    private String password;
    private String username;
}