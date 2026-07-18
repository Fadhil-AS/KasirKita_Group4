package com.mycompany.kasirkita.service;

import com.mycompany.kasirkita.dto.PembayaranRequest;
import com.mycompany.kasirkita.dto.PembayaranResponse;
import com.mycompany.kasirkita.entity.Pembayaran;
import com.mycompany.kasirkita.entity.Transaksi;
import com.mycompany.kasirkita.exception.BusinessRuleException;
import com.mycompany.kasirkita.exception.ResourceNotFoundException;
import com.mycompany.kasirkita.repository.PembayaranRepository;
import com.mycompany.kasirkita.repository.TransaksiRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PembayaranService {

    private final PembayaranRepository repo;
    private final TransaksiRepository transaksiRepo;

    public PembayaranService(PembayaranRepository repo, TransaksiRepository transaksiRepo) {
        this.repo = repo;
        this.transaksiRepo = transaksiRepo;
    }

    @Transactional
    public PembayaranResponse bayar(PembayaranRequest req) {
        Transaksi trx = transaksiRepo.findById(req.transaksiId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Transaksi id " + req.transaksiId() + " tidak ditemukan"));

        if (repo.existsByTransaksiId(trx.getId())) {
            throw new BusinessRuleException("Transaksi id " + trx.getId() + " sudah dibayar");
        }

        BigDecimal total = trx.getTotalHarga();
        // BR2: uang dibayar tidak boleh kurang dari total harga
        if (req.uangDibayar().compareTo(total) < 0) {
            throw new BusinessRuleException("Uang dibayar (" + req.uangDibayar()
                    + ") kurang dari total harga (" + total + ")");
        }

        // M7: satu rumus kembalian = uang_dibayar - total_harga
        BigDecimal kembalian = req.uangDibayar().subtract(total);

        Pembayaran p = new Pembayaran();
        p.setTransaksi(trx);
        p.setUangDibayar(req.uangDibayar());
        p.setKembalian(kembalian);
        p.setTglBayar(LocalDateTime.now());
        return PembayaranResponse.from(repo.save(p));
    }
}
