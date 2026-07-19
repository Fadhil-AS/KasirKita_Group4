package com.mycompany.kasirkita.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mycompany.kasirkita.dto.BarangRequest;
import com.mycompany.kasirkita.dto.BarangResponse;
import com.mycompany.kasirkita.entity.Barang;
import com.mycompany.kasirkita.entity.Supplier;
import com.mycompany.kasirkita.exception.ResourceNotFoundException;
import com.mycompany.kasirkita.repository.BarangRepository;
import com.mycompany.kasirkita.repository.SupplierRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BarangServiceTest {

    @Mock BarangRepository repo;
    @Mock SupplierRepository supplierRepo;
    @InjectMocks BarangService service;

    private Supplier supplier() {
        Supplier s = new Supplier();
        s.setId(1L);
        s.setNamaSupp("PT A");
        return s;
    }

    @Test
    void createMapsFieldsAndSupplier() {
        when(supplierRepo.findById(1L)).thenReturn(Optional.of(supplier()));
        when(repo.save(any(Barang.class))).thenAnswer(i -> i.getArgument(0));
        BarangResponse res = service.create(new BarangRequest("Indomie", new BigDecimal("3000"), 50, 1L));
        assertEquals("Indomie", res.namaBarang());
        assertEquals(50, res.stok());
        assertEquals(1L, res.supplierId());
        assertEquals("PT A", res.namaSupplier());
    }

    @Test
    void createSupplierNotFoundThrows() {
        when(supplierRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> service.create(new BarangRequest("X", new BigDecimal("1"), 1, 2L)));
        verify(repo, never()).save(any());
    }

    @Test
    void updateNotFoundThrows() {
        when(repo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> service.update(9L, new BarangRequest("X", new BigDecimal("1"), 1, 1L)));
    }

    @Test
    void deleteNotFoundThrows() {
        when(repo.existsById(9L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(9L));
    }

    @Test
    void deleteOkCallsRepo() {
        when(repo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(repo).deleteById(1L);
    }
}
