package com.mycompany.kasirkita.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mycompany.kasirkita.dto.TransaksiRequest;
import com.mycompany.kasirkita.dto.TransaksiResponse;
import com.mycompany.kasirkita.entity.Barang;
import com.mycompany.kasirkita.entity.JenisKelamin;
import com.mycompany.kasirkita.entity.Pembeli;
import com.mycompany.kasirkita.entity.Transaksi;
import com.mycompany.kasirkita.exception.BusinessRuleException;
import com.mycompany.kasirkita.exception.ResourceNotFoundException;
import com.mycompany.kasirkita.repository.BarangRepository;
import com.mycompany.kasirkita.repository.PembeliRepository;
import com.mycompany.kasirkita.repository.TransaksiRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransaksiServiceTest {

    @Mock TransaksiRepository repo;
    @Mock PembeliRepository pembeliRepo;
    @Mock BarangRepository barangRepo;
    @InjectMocks TransaksiService service;

    private Barang barang(int stok) {
        Barang b = new Barang();
        b.setId(1L);
        b.setNamaBarang("Indomie");
        b.setPrice(new BigDecimal("10000"));
        b.setStok(stok);
        return b;
    }

    private Pembeli pembeli() {
        Pembeli p = new Pembeli();
        p.setId(5L);
        p.setNamaPembeli("Budi");
        p.setJenisKelamin(JenisKelamin.L);
        return p;
    }

    private TransaksiRequest req(Long pembeliId, long barangId, int qty) {
        return new TransaksiRequest(pembeliId, List.of(new TransaksiRequest.Item(barangId, qty)));
    }

    @Test
    void createComputesTotalAndReducesStock() {
        Barang b = barang(10);
        when(pembeliRepo.findById(5L)).thenReturn(Optional.of(pembeli()));
        when(barangRepo.findById(1L)).thenReturn(Optional.of(b));
        when(repo.save(any(Transaksi.class))).thenAnswer(i -> i.getArgument(0));

        TransaksiResponse res = service.create(req(5L, 1L, 3));

        assertEquals(0, new BigDecimal("30000").compareTo(res.totalHarga()));
        assertEquals(7, b.getStok());
        assertEquals(1, res.items().size());
    }

    @Test
    void createStokTidakCukupThrows() {
        when(barangRepo.findById(1L)).thenReturn(Optional.of(barang(2)));
        assertThrows(BusinessRuleException.class, () -> service.create(req(null, 1L, 5)));
        verify(repo, never()).save(any());
    }

    @Test
    void createWalkInTanpaPembeli() {
        when(barangRepo.findById(1L)).thenReturn(Optional.of(barang(10)));
        when(repo.save(any(Transaksi.class))).thenAnswer(i -> i.getArgument(0));

        TransaksiResponse res = service.create(req(null, 1L, 1));

        assertNull(res.pembeliId());
        assertEquals(0, new BigDecimal("10000").compareTo(res.totalHarga()));
    }

    @Test
    void createBarangNotFoundThrows() {
        when(barangRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.create(req(null, 1L, 1)));
    }

    @Test
    void createPembeliNotFoundThrows() {
        when(pembeliRepo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.create(req(9L, 1L, 1)));
        verify(barangRepo, never()).findById(any());
    }
}
