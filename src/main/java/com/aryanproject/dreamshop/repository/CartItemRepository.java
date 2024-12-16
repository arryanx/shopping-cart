package com.aryanproject.dreamshop.repository;

import com.aryanproject.dreamshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
