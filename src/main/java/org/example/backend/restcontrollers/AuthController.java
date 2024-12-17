package org.example.backend.restcontrollers;

import org.example.backend.dtos.UserRegister;
import org.example.backend.entities.Role;
import org.example.backend.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserRegister user) {
        System.out.println("Registering user: " + user);
        try {
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                System.out.println("Username is required");
                return "Username is required";
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                System.out.println("Email is required");
                return "Email is required";
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                System.out.println("Password is required");
                return "Password is required";
            }

            System.out.println("Fetching role ORDINARY...");
            Role role = accountService.findRoleByRoleName("ORDINARY");
            if (role == null) {
                System.out.println("Role ORDINARY not found, creating it...");
                role = new Role("ORDINARY");
                accountService.saveRole(role);
                System.out.println("Role ORDINARY created successfully.");
            }
            user.setRoleName("ORDINARY");
            System.out.println("Saving user...");
            accountService.registerUser(user);
            System.out.println("User registered successfully.");
            return "User registered successfully";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Registration failed: " + e.getMessage());
            return "Registration failed: " + e.getMessage();
        }
    }
}
