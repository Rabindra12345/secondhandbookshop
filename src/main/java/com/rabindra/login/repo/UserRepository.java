package com.rabindra.login.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rabindra.login.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
