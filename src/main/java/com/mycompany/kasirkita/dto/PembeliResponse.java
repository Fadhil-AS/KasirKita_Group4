package com.mycompany.kasirkita.dto;

import com.mycompany.kasirkita.entity.JenisKelamin;
import com.mycompany.kasirkita.entity.Pembeli;

public record PembeliResponse(
        Long id,
        String namaPembeli,
        JenisKelamin jenisKelamin,
        String noTelepon,
        String alamat
) {
    public static PembeliResponse from(Pembeli p) {
        return new PembeliResponse(p.getId(), p.getNamaPembeli(), p.getJenisKelamin(),
                p.getNoTelepon(), p.getAlamat());
    }
}
