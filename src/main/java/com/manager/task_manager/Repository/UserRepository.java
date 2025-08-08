package com.manager.task_manager.Repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.manager.task_manager.Model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
