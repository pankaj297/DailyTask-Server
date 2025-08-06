package com.manager.task_manager.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manager.task_manager.Model.User;

@Repository
public  interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
}
