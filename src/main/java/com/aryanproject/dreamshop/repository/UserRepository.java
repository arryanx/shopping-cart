package com.aryanproject.dreamshop.repository;

import com.aryanproject.dreamshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
}
