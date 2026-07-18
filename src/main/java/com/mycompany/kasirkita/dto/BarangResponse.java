package com.mycompany.kasirkita.dto;

import com.mycompany.kasirkita.entity.Barang;
import java.math.BigDecimal;

public record BarangResponse(
        Long id,
        String namaBarang,
        BigDecimal price,
        int stok,
        Long supplierId,
        String namaSupplier
) {
    public static BarangResponse from(Barang b) {
        Long supId = b.getSupplier() != null ? b.getSupplier().getId() : null;
        String supNama = b.getSupplier() != null ? b.getSupplier().getNamaSupp() : null;
        return new BarangResponse(b.getId(), b.getNamaBarang(), b.getPrice(), b.getStok(), supId, supNama);
    }
}
