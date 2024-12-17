package org.example.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Transactional
public class User {
    @Id
    private String username;
    private String password;
    private String email;
    @ManyToOne
    private Role role;

    // Getters and setters
    // ...existing code...
}
