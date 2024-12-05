package com.aryanproject.dreamshop.repository;

import com.aryanproject.dreamshop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
