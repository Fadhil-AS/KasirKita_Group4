package com.mycompany.kasirkita.dto;

import com.mycompany.kasirkita.entity.JenisKelamin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PembeliRequest(
        @NotBlank String namaPembeli,
        @NotNull JenisKelamin jenisKelamin,
        @Size(max = 20) String noTelepon,
        String alamat
) {
}
