package com.mycompany.kasirkita.repository;

import com.mycompany.kasirkita.entity.Pembayaran;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PembayaranRepository extends JpaRepository<Pembayaran, Long> {
    boolean existsByTransaksiId(Long transaksiId);
}
