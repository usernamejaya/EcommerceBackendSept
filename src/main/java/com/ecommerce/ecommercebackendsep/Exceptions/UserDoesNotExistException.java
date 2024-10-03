package com.ecommerce.ecommercebackendsep.Exceptions;

public class UserDoesNotExistException extends  Exception{
    public UserDoesNotExistException(String message){
        super(message);
    }
}
