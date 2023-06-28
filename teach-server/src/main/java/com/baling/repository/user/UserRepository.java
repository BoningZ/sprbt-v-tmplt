package com.baling.repository.user;


import java.util.Optional;

import com.baling.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);


    Optional<User> findByUserId(Integer userId);

    Boolean existsByUsername(String username);
}
