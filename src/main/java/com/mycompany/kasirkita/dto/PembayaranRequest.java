package com.mycompany.kasirkita.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PembayaranRequest(
        @NotNull Long transaksiId,
        @NotNull @Positive BigDecimal uangDibayar
) {
}
