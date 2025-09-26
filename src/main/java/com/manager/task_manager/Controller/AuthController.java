package com.manager.task_manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.manager.task_manager.Helper.UploadHelper;
import com.manager.task_manager.Model.User;
import com.manager.task_manager.Services.UserService;


import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadHelper uploadHelper;


    

    @PostMapping("/register")
    public ResponseEntity<String> addUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("file") MultipartFile file) {

        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("🚫 File Not Found!");
            }

            if (!file.getContentType().startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("📸 Only Image Files Allowed!");
            }

            // Create clean username for filename
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String safeName = name.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", "");
            String filename = System.currentTimeMillis() + "_" + safeName + extension;

            // Upload image
            boolean uploaded = uploadHelper.uploadFile(file, filename);
            if (!uploaded) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("⚠️ File Upload Failed!");
            }

            // Create and save user
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setImagePath(filename);
            user.setRole("ROLE_USER");

            userService.createUser(user);

            String imageUrl = "http://localhost:8080/image/" + filename;
            return ResponseEntity.ok("✅ User Registered. Image URL: " + imageUrl);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Something Went Wrong!");
        }
    }

    

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            if (user == null || user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email और Password जरूरी हैं");
            }

            Optional<User> existing = userService.findByEmail(user.getEmail());

            if (existing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email");
            }

            if (existing.get().getPassword() == null ||
                    !existing.get().getPassword().equals(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }

            return ResponseEntity.ok(existing.get());

        } catch (Exception e) {
            e.printStackTrace(); // Console में error दिखाने के लिए
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error: " + e.getMessage());
        }
    }



}
