package com.manager.task_manager.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.task_manager.Model.User;
import com.manager.task_manager.Repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public User registerUser(User user) {
        return userRepo.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }
    
    public User createUser(User user) {
        return userRepo.save(user);
    }

}