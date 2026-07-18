package com.mycompany.kasirkita.service;

import com.mycompany.kasirkita.dto.BarangRequest;
import com.mycompany.kasirkita.dto.BarangResponse;
import com.mycompany.kasirkita.entity.Barang;
import com.mycompany.kasirkita.entity.Supplier;
import com.mycompany.kasirkita.exception.ResourceNotFoundException;
import com.mycompany.kasirkita.repository.BarangRepository;
import com.mycompany.kasirkita.repository.SupplierRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BarangService {

    private final BarangRepository repo;
    private final SupplierRepository supplierRepo;

    public BarangService(BarangRepository repo, SupplierRepository supplierRepo) {
        this.repo = repo;
        this.supplierRepo = supplierRepo;
    }

    public List<BarangResponse> list() {
        return repo.findAll().stream().map(BarangResponse::from).toList();
    }

    public BarangResponse get(Long id) {
        return BarangResponse.from(find(id));
    }

    public BarangResponse create(BarangRequest req) {
        Barang b = new Barang();
        apply(b, req);
        return BarangResponse.from(repo.save(b));
    }

    public BarangResponse update(Long id, BarangRequest req) {
        Barang b = find(id);
        apply(b, req);
        return BarangResponse.from(repo.save(b));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Barang id " + id + " tidak ditemukan");
        }
        repo.deleteById(id);
    }

    private Barang find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barang id " + id + " tidak ditemukan"));
    }

    private void apply(Barang b, BarangRequest req) {
        b.setNamaBarang(req.namaBarang());
        b.setPrice(req.price());
        b.setStok(req.stok());
        // Barang wajib punya supplier
        Supplier s = supplierRepo.findById(req.supplierId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Supplier id " + req.supplierId() + " tidak ditemukan"));
        b.setSupplier(s);
    }
}
