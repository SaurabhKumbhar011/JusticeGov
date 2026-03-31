package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Import the JPA interface
import org.springframework.stereotype.Repository;

import com.justicegov.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //adding a custom finder to search for Compliance Officers
    User findByRole(String role);
}