package com.mycompany.kasirkita.service;

import com.mycompany.kasirkita.dto.TransaksiRequest;
import com.mycompany.kasirkita.dto.TransaksiResponse;
import com.mycompany.kasirkita.entity.Barang;
import com.mycompany.kasirkita.entity.DetailTransaksi;
import com.mycompany.kasirkita.entity.Pembeli;
import com.mycompany.kasirkita.entity.Transaksi;
import com.mycompany.kasirkita.exception.BusinessRuleException;
import com.mycompany.kasirkita.exception.ResourceNotFoundException;
import com.mycompany.kasirkita.repository.BarangRepository;
import com.mycompany.kasirkita.repository.PembeliRepository;
import com.mycompany.kasirkita.repository.TransaksiRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransaksiService {

    private final TransaksiRepository repo;
    private final PembeliRepository pembeliRepo;
    private final BarangRepository barangRepo;

    public TransaksiService(TransaksiRepository repo, PembeliRepository pembeliRepo, BarangRepository barangRepo) {
        this.repo = repo;
        this.pembeliRepo = pembeliRepo;
        this.barangRepo = barangRepo;
    }

    @Transactional(readOnly = true)
    public List<TransaksiResponse> list() {
        return repo.findAll().stream().map(TransaksiResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TransaksiResponse get(Long id) {
        Transaksi t = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaksi id " + id + " tidak ditemukan"));
        return TransaksiResponse.from(t);
    }

    @Transactional
    public TransaksiResponse create(TransaksiRequest req) {
        Transaksi trx = new Transaksi();
        // Pembeli opsional: transaksi tanpa nama pembeli (umum/walk-in) diizinkan
        if (req.pembeliId() != null) {
            Pembeli pembeli = pembeliRepo.findById(req.pembeliId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Pembeli id " + req.pembeliId() + " tidak ditemukan"));
            trx.setPembeli(pembeli);
        }
        trx.setTanggal(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        for (TransaksiRequest.Item item : req.items()) {
            Barang barang = barangRepo.findById(item.barangId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Barang id " + item.barangId() + " tidak ditemukan"));

            // BR3: stok tidak boleh negatif
            if (barang.getStok() < item.quantity()) {
                throw new BusinessRuleException("Stok barang '" + barang.getNamaBarang()
                        + "' tidak cukup (sisa " + barang.getStok() + ", diminta " + item.quantity() + ")");
            }
            barang.setStok(barang.getStok() - item.quantity());

            // BR1: subtotal = quantity x price
            BigDecimal subtotal = barang.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
            total = total.add(subtotal);

            DetailTransaksi detail = new DetailTransaksi();
            detail.setBarang(barang);
            detail.setQuantity(item.quantity());
            detail.setSubtotal(subtotal);
            trx.addItem(detail);
        }

        trx.setTotalHarga(total);
        return TransaksiResponse.from(repo.save(trx));
    }
}
