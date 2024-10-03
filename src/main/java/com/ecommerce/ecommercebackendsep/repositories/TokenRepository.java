package com.ecommerce.ecommercebackendsep.repositories;

import com.ecommerce.ecommercebackendsep.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndDeletedEquals(String tokenValue, boolean deleted);
    Optional<Token> findByValueAndDeletedEqualsAndExpiryAtGreaterThan(String token, boolean isDeleted, Date expiryGreaterThan);
}
