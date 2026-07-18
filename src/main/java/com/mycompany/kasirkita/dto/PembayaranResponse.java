package com.mycompany.kasirkita.dto;

import com.mycompany.kasirkita.entity.Pembayaran;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PembayaranResponse(
        Long id,
        Long transaksiId,
        BigDecimal totalHarga,
        BigDecimal uangDibayar,
        BigDecimal kembalian,
        LocalDateTime tglBayar
) {
    public static PembayaranResponse from(Pembayaran p) {
        return new PembayaranResponse(
                p.getId(),
                p.getTransaksi().getId(),
                p.getTransaksi().getTotalHarga(),
                p.getUangDibayar(),
                p.getKembalian(),
                p.getTglBayar());
    }
}
