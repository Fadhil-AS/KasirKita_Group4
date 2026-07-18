package com.mycompany.kasirkita.service;

import com.mycompany.kasirkita.dto.PembeliRequest;
import com.mycompany.kasirkita.dto.PembeliResponse;
import com.mycompany.kasirkita.entity.Pembeli;
import com.mycompany.kasirkita.exception.ResourceNotFoundException;
import com.mycompany.kasirkita.repository.PembeliRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PembeliService {

    private final PembeliRepository repo;

    public PembeliService(PembeliRepository repo) {
        this.repo = repo;
    }

    public List<PembeliResponse> list() {
        return repo.findAll().stream().map(PembeliResponse::from).toList();
    }

    public PembeliResponse get(Long id) {
        return PembeliResponse.from(find(id));
    }

    public PembeliResponse create(PembeliRequest req) {
        Pembeli p = new Pembeli();
        apply(p, req);
        return PembeliResponse.from(repo.save(p));
    }

    public PembeliResponse update(Long id, PembeliRequest req) {
        Pembeli p = find(id);
        apply(p, req);
        return PembeliResponse.from(repo.save(p));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Pembeli id " + id + " tidak ditemukan");
        }
        repo.deleteById(id);
    }

    private Pembeli find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pembeli id " + id + " tidak ditemukan"));
    }

    private void apply(Pembeli p, PembeliRequest req) {
        p.setNamaPembeli(req.namaPembeli());
        p.setJenisKelamin(req.jenisKelamin());
        p.setNoTelepon(req.noTelepon());
        p.setAlamat(req.alamat());
    }
}
