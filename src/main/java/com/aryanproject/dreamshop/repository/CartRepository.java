package com.aryanproject.dreamshop.repository;

import com.aryanproject.dreamshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findUserById(Long userId);
}
