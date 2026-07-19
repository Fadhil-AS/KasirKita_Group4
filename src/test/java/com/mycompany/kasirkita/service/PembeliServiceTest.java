package com.mycompany.kasirkita.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mycompany.kasirkita.dto.PembeliRequest;
import com.mycompany.kasirkita.dto.PembeliResponse;
import com.mycompany.kasirkita.entity.JenisKelamin;
import com.mycompany.kasirkita.entity.Pembeli;
import com.mycompany.kasirkita.exception.ResourceNotFoundException;
import com.mycompany.kasirkita.repository.PembeliRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PembeliServiceTest {

    @Mock PembeliRepository repo;
    @InjectMocks PembeliService service;

    @Test
    void createMapsFields() {
        when(repo.save(any(Pembeli.class))).thenAnswer(i -> i.getArgument(0));
        PembeliResponse res = service.create(
                new PembeliRequest("Budi Santoso", JenisKelamin.L, "0813", "Depok"));
        assertEquals("Budi Santoso", res.namaPembeli());
        assertEquals(JenisKelamin.L, res.jenisKelamin());
        assertEquals("0813", res.noTelepon());
        assertEquals("Depok", res.alamat());
    }

    @Test
    void getNotFoundThrows() {
        when(repo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.get(9L));
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
