package com.aryanproject.dreamshop.repository;

import com.aryanproject.dreamshop.model.Category;
import com.aryanproject.dreamshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
