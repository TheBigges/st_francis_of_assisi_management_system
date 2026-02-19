package com.calapi.st.francis.assisi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.calapi.st.francis.assisi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
