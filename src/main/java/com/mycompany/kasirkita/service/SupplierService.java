package com.mycompany.kasirkita.service;

import com.mycompany.kasirkita.dto.SupplierRequest;
import com.mycompany.kasirkita.dto.SupplierResponse;
import com.mycompany.kasirkita.entity.Supplier;
import com.mycompany.kasirkita.exception.BusinessRuleException;
import com.mycompany.kasirkita.exception.ResourceNotFoundException;
import com.mycompany.kasirkita.repository.BarangRepository;
import com.mycompany.kasirkita.repository.SupplierRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    private final SupplierRepository repo;
    private final BarangRepository barangRepo;

    public SupplierService(SupplierRepository repo, BarangRepository barangRepo) {
        this.repo = repo;
        this.barangRepo = barangRepo;
    }

    public List<SupplierResponse> list() {
        return repo.findAll().stream().map(SupplierResponse::from).toList();
    }

    public SupplierResponse get(Long id) {
        return SupplierResponse.from(find(id));
    }

    public SupplierResponse create(SupplierRequest req) {
        Supplier s = new Supplier();
        apply(s, req);
        return SupplierResponse.from(repo.save(s));
    }

    public SupplierResponse update(Long id, SupplierRequest req) {
        Supplier s = find(id);
        apply(s, req);
        return SupplierResponse.from(repo.save(s));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Supplier id " + id + " tidak ditemukan");
        }
        if (barangRepo.existsBySupplierId(id)) {
            throw new BusinessRuleException("Supplier masih dipakai oleh barang. Hapus/ubah barang terkait dulu.");
        }
        repo.deleteById(id);
    }

    private Supplier find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier id " + id + " tidak ditemukan"));
    }

    private void apply(Supplier s, SupplierRequest req) {
        s.setNamaSupp(req.namaSupp());
        s.setNoTelp(req.noTelp());
        s.setAlamat(req.alamat());
    }
}
