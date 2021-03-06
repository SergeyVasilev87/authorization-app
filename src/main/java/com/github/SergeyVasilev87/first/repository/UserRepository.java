package com.github.SergeyVasilev87.first.repository;

import com.github.SergeyVasilev87.first.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
