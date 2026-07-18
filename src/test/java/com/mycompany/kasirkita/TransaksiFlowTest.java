package com.mycompany.kasirkita;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mycompany.kasirkita.dto.BarangRequest;
import com.mycompany.kasirkita.dto.PembayaranRequest;
import com.mycompany.kasirkita.dto.PembeliRequest;
import com.mycompany.kasirkita.dto.PembeliResponse;
import com.mycompany.kasirkita.dto.SupplierResponse;
import com.mycompany.kasirkita.dto.SupplierRequest;
import com.mycompany.kasirkita.dto.TransaksiRequest;
import com.mycompany.kasirkita.dto.TransaksiResponse;
import com.mycompany.kasirkita.entity.JenisKelamin;
import com.mycompany.kasirkita.exception.BusinessRuleException;
import com.mycompany.kasirkita.service.BarangService;
import com.mycompany.kasirkita.service.PembayaranService;
import com.mycompany.kasirkita.service.PembeliService;
import com.mycompany.kasirkita.service.SupplierService;
import com.mycompany.kasirkita.service.TransaksiService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TransaksiFlowTest {

    @Autowired SupplierService supplierService;
    @Autowired BarangService barangService;
    @Autowired PembeliService pembeliService;
    @Autowired TransaksiService transaksiService;
    @Autowired PembayaranService pembayaranService;

    @Test
    void transaksiKurangiStokDanHitungTotalDanKembalian() {
        SupplierResponse sup = supplierService.create(
                new SupplierRequest("PT Indofood", "08123456789", "Jakarta"));
        var barang = barangService.create(
                new BarangRequest("Indomie", new BigDecimal("10000.00"), 10, sup.id()));
        PembeliResponse pembeli = pembeliService.create(
                new PembeliRequest("Risky", JenisKelamin.L, "08987654321", "Bandung"));

        // BR1 + BR3: beli 3 -> total 30000, stok 10 -> 7
        TransaksiRequest req = new TransaksiRequest(pembeli.id(),
                List.of(new TransaksiRequest.Item(barang.id(), 3)));
        TransaksiResponse trx = transaksiService.create(req);

        assertEquals(0, new BigDecimal("30000").compareTo(trx.totalHarga()));
        assertEquals(7, barangService.get(barang.id()).stok());

        // BR2 + M7: bayar 50000 -> kembalian 20000
        var bayar = pembayaranService.bayar(new PembayaranRequest(trx.id(), new BigDecimal("50000")));
        assertEquals(0, new BigDecimal("20000").compareTo(bayar.kembalian()));
    }

    @Test
    void bayarKurangDariTotalDitolak() {
        SupplierResponse sup = supplierService.create(
                new SupplierRequest("Supp", "0812", "Kota"));
        var barang = barangService.create(
                new BarangRequest("Teh", new BigDecimal("5000.00"), 5, sup.id()));
        PembeliResponse pembeli = pembeliService.create(
                new PembeliRequest("Budi", JenisKelamin.L, "0899", "Kota"));
        TransaksiResponse trx = transaksiService.create(new TransaksiRequest(pembeli.id(),
                List.of(new TransaksiRequest.Item(barang.id(), 2))));

        // total 10000, bayar 5000 -> ditolak
        BusinessRuleException ex = assertThrows(BusinessRuleException.class,
                () -> pembayaranService.bayar(new PembayaranRequest(trx.id(), new BigDecimal("5000"))));
        assertTrue(ex.getMessage().contains("kurang"));
    }

    @Test
    void stokTidakCukupDitolak() {
        SupplierResponse sup = supplierService.create(
                new SupplierRequest("Supp", "0812", "Kota"));
        var barang = barangService.create(
                new BarangRequest("Kopi", new BigDecimal("3000.00"), 2, sup.id()));
        PembeliResponse pembeli = pembeliService.create(
                new PembeliRequest("Ani", JenisKelamin.P, "0899", "Kota"));

        assertThrows(BusinessRuleException.class,
                () -> transaksiService.create(new TransaksiRequest(pembeli.id(),
                        List.of(new TransaksiRequest.Item(barang.id(), 5)))));
    }
}
