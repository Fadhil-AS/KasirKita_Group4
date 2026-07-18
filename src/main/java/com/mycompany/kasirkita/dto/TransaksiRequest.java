package com.mycompany.kasirkita.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record TransaksiRequest(
        Long pembeliId,
        @NotEmpty @Valid List<Item> items
) {
    public record Item(
            @NotNull Long barangId,
            @Positive int quantity
    ) {
    }
}
