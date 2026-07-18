package com.mycompany.kasirkita.service;

import com.mycompany.kasirkita.dto.AuthResponse;
import com.mycompany.kasirkita.dto.LoginRequest;
import com.mycompany.kasirkita.dto.RegisterRequest;
import com.mycompany.kasirkita.entity.User;
import com.mycompany.kasirkita.exception.BusinessRuleException;
import com.mycompany.kasirkita.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public AuthResponse register(RegisterRequest req) {
        if (repo.existsByUsername(req.username())) {
            throw new BusinessRuleException("Username '" + req.username() + "' sudah dipakai");
        }
        User u = new User();
        u.setUsername(req.username());
        u.setPasswordHash(encoder.encode(req.password()));
        u.setRole(normalizeRole(req.role()));
        repo.save(u);
        return new AuthResponse(u.getId(), u.getUsername(), u.getRole(), "Registrasi berhasil");
    }

    public AuthResponse login(LoginRequest req) {
        User u = repo.findByUsername(req.username())
                .filter(user -> encoder.matches(req.password(), user.getPasswordHash()))
                .orElseThrow(() -> new BusinessRuleException("Username atau password salah"));
        return new AuthResponse(u.getId(), u.getUsername(), u.getRole(), "Login berhasil");
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            return "KASIR";
        }
        return role.trim().toUpperCase();
    }
}
