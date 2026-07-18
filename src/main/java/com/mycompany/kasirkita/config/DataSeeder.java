package com.mycompany.kasirkita.config;

import com.mycompany.kasirkita.entity.User;
import com.mycompany.kasirkita.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final String adminUsername;
    private final String adminPassword;

    public DataSeeder(UserRepository userRepo, PasswordEncoder encoder,
                      @Value("${kasirkita.seed.admin-username}") String adminUsername,
                      @Value("${kasirkita.seed.admin-password}") String adminPassword) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        if (userRepo.existsByUsername(adminUsername)) {
            return;
        }
        User admin = new User();
        admin.setUsername(adminUsername);
        admin.setPasswordHash(encoder.encode(adminPassword));
        admin.setRole("ADMIN");
        userRepo.save(admin);
    }
}
