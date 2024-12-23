package org.example.backend.restcontrollers;

import org.example.backend.dtos.UserRegister;
import org.example.backend.dtos.UserLogin;
import org.example.backend.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserRegister user) {
        System.out.println("Registering user: " + user);
        Map<String, String> response = new HashMap<>();
        try {
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                System.out.println("Username is required");
                response.put("message", "Username is required");
                return ResponseEntity.badRequest().body(response);
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                System.out.println("Email is required");
                response.put("message", "Email is required");
                return ResponseEntity.badRequest().body(response);
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                System.out.println("Password is required");
                response.put("message", "Password is required");
                return ResponseEntity.badRequest().body(response);
            }
            if (accountService.findUserByUsername(user.getUsername()) != null) {
                System.out.println("Username already exists");
                response.put("message", "Username already exists");
                return ResponseEntity.badRequest().body(response);
            }
            user.setRoleName("ORDINARY");
            System.out.println("Saving user...");
            accountService.registerUser(user);
            System.out.println("User registered successfully.");
            response.put("message", "User registered successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Registration failed: " + e.getMessage());
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserLogin userLogin) {
        System.out.println("Logging in user: " + userLogin);
        Map<String, String> response = new HashMap<>();
        try {
            String token = accountService.loginUser(userLogin.getUsername(), userLogin.getPassword());
            if (token == null) {
                response.put("message", "Invalid username or password");
                return ResponseEntity.badRequest().body(response);
            }
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Login failed: " + e.getMessage());
            response.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
