package com.sravan.Secure.File.Storage.repository;

import com.sravan.Secure.File.Storage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
