package com.mycompany.kasirkita.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record BarangRequest(
        @NotBlank String namaBarang,
        @NotNull @PositiveOrZero BigDecimal price,
        @PositiveOrZero int stok,
        @NotNull Long supplierId
) {
}
