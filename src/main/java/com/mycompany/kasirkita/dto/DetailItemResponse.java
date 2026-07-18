package com.mycompany.kasirkita.dto;

import com.mycompany.kasirkita.entity.DetailTransaksi;
import java.math.BigDecimal;

public record DetailItemResponse(
        Long barangId,
        String namaBarang,
        int quantity,
        BigDecimal subtotal
) {
    public static DetailItemResponse from(DetailTransaksi d) {
        return new DetailItemResponse(d.getBarang().getId(), d.getBarang().getNamaBarang(),
                d.getQuantity(), d.getSubtotal());
    }
}
