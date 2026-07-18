package com.mycompany.kasirkita.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SupplierRequest(
        @NotBlank String namaSupp,
        @Size(max = 20) String noTelp,
        String alamat
) {
}
