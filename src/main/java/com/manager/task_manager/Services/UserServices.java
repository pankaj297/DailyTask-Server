package com.manager.task_manager.Services;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.task_manager.Model.User;
import com.manager.task_manager.Repository.UserRepository;

@Service
public class UserServices {
    
    @Autowired
    private UserRepository userRepository;
 

    
@Autowired
private PasswordEncoder passwordEncoder;


    //* get User
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
    //* Create User */
    public User createUser(User user) {
        // ✅ Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public User getUserByEmailPassword(String email, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (rawPassword.equals(user.getPassword())) {
                return user; // ✅ Login success
            }
        }
        return null;
    }

    public User getUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserPrincipalNotFoundException("User not found with email: " + email));
        } catch (UserPrincipalNotFoundException e) {
            e.printStackTrace();
            return null;
        }
      
    }
    
    
}
