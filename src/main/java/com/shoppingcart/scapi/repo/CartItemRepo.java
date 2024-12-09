package com.shoppingcart.scapi.repo;

import com.shoppingcart.scapi.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
