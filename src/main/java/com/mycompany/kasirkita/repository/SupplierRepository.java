package com.mycompany.kasirkita.repository;

import com.mycompany.kasirkita.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
