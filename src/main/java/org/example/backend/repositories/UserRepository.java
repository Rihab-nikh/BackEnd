package org.example.backend.repositories;

import org.example.backend.entities.Role;
import org.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<Role> findRolesByUsername(String username);
}
