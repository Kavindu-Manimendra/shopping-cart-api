package com.shoppingcart.scapi.repo;

import com.shoppingcart.scapi.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Long> {
}
