package com.mycompany.kasirkita.dto;

import com.mycompany.kasirkita.entity.Supplier;

public record SupplierResponse(
        Long id,
        String namaSupp,
        String noTelp,
        String alamat
) {
    public static SupplierResponse from(Supplier s) {
        return new SupplierResponse(s.getId(), s.getNamaSupp(), s.getNoTelp(), s.getAlamat());
    }
}
