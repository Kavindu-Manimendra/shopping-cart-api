package com.shoppingcart.scapi.repo;

import com.shoppingcart.scapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
