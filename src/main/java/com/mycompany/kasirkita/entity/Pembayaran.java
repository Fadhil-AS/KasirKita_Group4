package com.mycompany.kasirkita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pembayaran")
public class Pembayaran extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaksi_id", nullable = false)
    private Transaksi transaksi;

    @Column(name = "uang_dibayar", nullable = false, precision = 15, scale = 2)
    private BigDecimal uangDibayar;

    @Column(name = "kembalian", nullable = false, precision = 15, scale = 2)
    private BigDecimal kembalian;

    @Column(name = "tgl_bayar", nullable = false)
    private LocalDateTime tglBayar;

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public BigDecimal getUangDibayar() {
        return uangDibayar;
    }

    public void setUangDibayar(BigDecimal uangDibayar) {
        this.uangDibayar = uangDibayar;
    }

    public BigDecimal getKembalian() {
        return kembalian;
    }

    public void setKembalian(BigDecimal kembalian) {
        this.kembalian = kembalian;
    }

    public LocalDateTime getTglBayar() {
        return tglBayar;
    }

    public void setTglBayar(LocalDateTime tglBayar) {
        this.tglBayar = tglBayar;
    }
}
