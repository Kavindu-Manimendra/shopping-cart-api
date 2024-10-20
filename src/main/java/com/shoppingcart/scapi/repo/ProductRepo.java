package com.shoppingcart.scapi.repo;

import com.shoppingcart.scapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
