package org.example.backend;

import org.example.backend.entities.Role;
import org.example.backend.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BackEndApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BackEndApplication.class);

    @Autowired
    private AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Application started");
        Role role = accountService.findRoleByRoleName("ORDINARY");
        if (role == null) {
            logger.info("Role ORDINARY not found, creating...");
            role = new Role("ORDINARY");
            accountService.saveRole(role);
            logger.info("Role ORDINARY created");
        } else {
            logger.info("Role ORDINARY found");
        }
    }
}