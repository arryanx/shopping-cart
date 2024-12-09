package com.aryanproject.dreamshop.repository;

import com.aryanproject.dreamshop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findProductById(Long id);
}
