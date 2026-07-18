package com.mycompany.kasirkita.dto;

import com.mycompany.kasirkita.entity.Transaksi;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record TransaksiResponse(
        Long id,
        Long pembeliId,
        String namaPembeli,
        LocalDateTime tanggal,
        BigDecimal totalHarga,
        List<DetailItemResponse> items
) {
    public static TransaksiResponse from(Transaksi t) {
        Long pembeliId = t.getPembeli() != null ? t.getPembeli().getId() : null;
        String namaPembeli = t.getPembeli() != null ? t.getPembeli().getNamaPembeli() : null;
        List<DetailItemResponse> items = t.getItems().stream()
                .map(DetailItemResponse::from)
                .toList();
        return new TransaksiResponse(t.getId(), pembeliId, namaPembeli, t.getTanggal(),
                t.getTotalHarga(), items);
    }
}
