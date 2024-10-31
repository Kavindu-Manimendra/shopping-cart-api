package com.shoppingcart.scapi.repo;

import com.shoppingcart.scapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
