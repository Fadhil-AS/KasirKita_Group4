package com.mycompany.kasirkita.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mycompany.kasirkita.dto.SupplierRequest;
import com.mycompany.kasirkita.dto.SupplierResponse;
import com.mycompany.kasirkita.entity.Supplier;
import com.mycompany.kasirkita.exception.BusinessRuleException;
import com.mycompany.kasirkita.exception.ResourceNotFoundException;
import com.mycompany.kasirkita.repository.BarangRepository;
import com.mycompany.kasirkita.repository.SupplierRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @Mock SupplierRepository repo;
    @Mock BarangRepository barangRepo;
    @InjectMocks SupplierService service;

    @Test
    void createMapsFields() {
        when(repo.save(any(Supplier.class))).thenAnswer(i -> i.getArgument(0));
        SupplierResponse res = service.create(new SupplierRequest("PT A", "0812", "Jakarta"));
        assertEquals("PT A", res.namaSupp());
        assertEquals("0812", res.noTelp());
        assertEquals("Jakarta", res.alamat());
    }

    @Test
    void getNotFoundThrows() {
        when(repo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.get(9L));
    }

    @Test
    void deleteInUseThrows() {
        when(repo.existsById(1L)).thenReturn(true);
        when(barangRepo.existsBySupplierId(1L)).thenReturn(true);
        assertThrows(BusinessRuleException.class, () -> service.delete(1L));
        verify(repo, never()).deleteById(any());
    }

    @Test
    void deleteOkCallsRepo() {
        when(repo.existsById(1L)).thenReturn(true);
        when(barangRepo.existsBySupplierId(1L)).thenReturn(false);
        service.delete(1L);
        verify(repo).deleteById(1L);
    }

    @Test
    void deleteNotFoundThrows() {
        when(repo.existsById(9L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(9L));
    }
}
