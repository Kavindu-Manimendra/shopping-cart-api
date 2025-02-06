package com.shoppingcart.scapi.repo;

import com.shoppingcart.scapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String roleUser);
}
