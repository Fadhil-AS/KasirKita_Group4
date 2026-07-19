package com.mycompany.kasirkita.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mycompany.kasirkita.dto.PembayaranRequest;
import com.mycompany.kasirkita.dto.PembayaranResponse;
import com.mycompany.kasirkita.entity.Pembayaran;
import com.mycompany.kasirkita.entity.Transaksi;
import com.mycompany.kasirkita.exception.BusinessRuleException;
import com.mycompany.kasirkita.exception.ResourceNotFoundException;
import com.mycompany.kasirkita.repository.PembayaranRepository;
import com.mycompany.kasirkita.repository.TransaksiRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PembayaranServiceTest {

    @Mock PembayaranRepository repo;
    @Mock TransaksiRepository transaksiRepo;
    @InjectMocks PembayaranService service;

    private Transaksi trx() {
        Transaksi t = new Transaksi();
        t.setId(1L);
        t.setTotalHarga(new BigDecimal("30000"));
        return t;
    }

    @Test
    void bayarComputesKembalian() {
        when(transaksiRepo.findById(1L)).thenReturn(Optional.of(trx()));
        when(repo.existsByTransaksiId(1L)).thenReturn(false);
        when(repo.save(any(Pembayaran.class))).thenAnswer(i -> i.getArgument(0));

        PembayaranResponse res = service.bayar(new PembayaranRequest(1L, new BigDecimal("50000")));

        assertEquals(0, new BigDecimal("20000").compareTo(res.kembalian()));
        assertEquals(0, new BigDecimal("30000").compareTo(res.totalHarga()));
    }

    @Test
    void bayarKurangDitolak() {
        when(transaksiRepo.findById(1L)).thenReturn(Optional.of(trx()));
        when(repo.existsByTransaksiId(1L)).thenReturn(false);
        BusinessRuleException ex = assertThrows(BusinessRuleException.class,
                () -> service.bayar(new PembayaranRequest(1L, new BigDecimal("5000"))));
        assertTrue(ex.getMessage().contains("kurang"));
        verify(repo, never()).save(any());
    }

    @Test
    void bayarSudahDibayarDitolak() {
        when(transaksiRepo.findById(1L)).thenReturn(Optional.of(trx()));
        when(repo.existsByTransaksiId(1L)).thenReturn(true);
        BusinessRuleException ex = assertThrows(BusinessRuleException.class,
                () -> service.bayar(new PembayaranRequest(1L, new BigDecimal("50000"))));
        assertTrue(ex.getMessage().contains("sudah dibayar"));
    }

    @Test
    void bayarTransaksiNotFoundThrows() {
        when(transaksiRepo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> service.bayar(new PembayaranRequest(9L, new BigDecimal("1"))));
    }
}
