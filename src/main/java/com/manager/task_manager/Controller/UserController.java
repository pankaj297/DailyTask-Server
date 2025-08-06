package com.manager.task_manager.Controller;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manager.task_manager.Helper.UploadHelper;
import com.manager.task_manager.Model.User;
import com.manager.task_manager.Services.UserServices;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*") // Allow React frontend
public class UserController {
    
    @Autowired
    private UserServices userServices;

    @Autowired
    private UploadHelper uploadHelper;

    //* Get User */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userServices.getUser(id);
        return user == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(user);
    }

    //* Add User with image
    @SuppressWarnings("null")
    @PostMapping("/register")
    public ResponseEntity<String> addUser(@ModelAttribute User user, @RequestParam("file") MultipartFile file) {

        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("🚫Files Not found...!");
            }

            if (file == null || file.getContentType() == null || !file.getContentType().startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("📸 Only Image File Allowed!");
            }

            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.indexOf("."));
            }

            // Example - Pankaj Naik  to Pankaj_Naik
            String userName = (user.getUsername() != null) ? user.getUsername().replaceAll("\\s+", "_") : "user";

            //This code are remove extra symbols like PankajNaik@122  to PankajNaik_122 remove : this symbols
            userName = userName.replaceAll("[^a-zA-Z0-9_]", "");

            // ✅ Set reverse reference for OneToOne Movie
            //Example - 1294934985_movieName.jpg , Example 2 - 122343424_KGF_Chapter_2.jpg
            String filename = System.currentTimeMillis() + "_" + userName + extension;

            //This code convert filename to String set as path
            filename = Paths.get(filename).getFileName().toString();
            user.setImagePath(filename);

            if (uploadHelper.uploadFile(file, filename)) {
                userServices.createUser(user);
                String imageUrl = "http://localhost:8080/image/" + filename;

                return ResponseEntity.ok("✅ User added. Image URL: " + imageUrl);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("file Upload failed...!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }
    

    //*User Login */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam("email") String email,
            @RequestParam("password") String password) {

        try {
            User user = userServices.getUserByEmailPassword(email, password);

            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid User...!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("⚠️ Something went wrong during login.");
        }
    }

}


