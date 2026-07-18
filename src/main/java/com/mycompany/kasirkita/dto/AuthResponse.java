package com.mycompany.kasirkita.dto;

public record AuthResponse(
        Long id,
        String username,
        String role,
        String message
) {
}
