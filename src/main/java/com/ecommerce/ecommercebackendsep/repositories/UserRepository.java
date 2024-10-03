package com.ecommerce.ecommercebackendsep.repositories;

import com.ecommerce.ecommercebackendsep.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
