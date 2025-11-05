package com.scoreshelf.scorestore.base.repository;

import com.scoreshelf.scorestore.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}