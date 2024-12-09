package com.shoppingcart.scapi.repo;

import com.shoppingcart.scapi.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
}
