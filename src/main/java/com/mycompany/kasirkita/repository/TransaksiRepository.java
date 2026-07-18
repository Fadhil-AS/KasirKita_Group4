package com.mycompany.kasirkita.repository;

import com.mycompany.kasirkita.entity.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {
}
