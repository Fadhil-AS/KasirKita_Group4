package com.mycompany.kasirkita.repository;

import com.mycompany.kasirkita.entity.Barang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarangRepository extends JpaRepository<Barang, Long> {
    boolean existsBySupplierId(Long supplierId);
}
