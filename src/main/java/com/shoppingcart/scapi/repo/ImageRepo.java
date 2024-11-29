package com.shoppingcart.scapi.repo;

import com.shoppingcart.scapi.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
