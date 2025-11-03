package org.gszabi15.config;

import org.gszabi15.model.entity.User;
import org.gszabi15.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@example.com";

            if (!userRepository.existsByEmail(adminEmail)) {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setName("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles("ROLE_USER,ROLE_ADMIN");

                userRepository.save(admin);
            }
        };
    }
}