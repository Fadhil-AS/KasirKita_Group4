package com.mycompany.kasirkita.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaksi")
public class Transaksi extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pembeli_id")
    private Pembeli pembeli;

    @Column(name = "tanggal", nullable = false)
    private LocalDateTime tanggal;

    @Column(name = "total_harga", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalHarga = BigDecimal.ZERO;

    @OneToMany(mappedBy = "transaksi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailTransaksi> items = new ArrayList<>();

    public void addItem(DetailTransaksi item) {
        item.setTransaksi(this);
        this.items.add(item);
    }

    public Pembeli getPembeli() {
        return pembeli;
    }

    public void setPembeli(Pembeli pembeli) {
        this.pembeli = pembeli;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public BigDecimal getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(BigDecimal totalHarga) {
        this.totalHarga = totalHarga;
    }

    public List<DetailTransaksi> getItems() {
        return items;
    }

    public void setItems(List<DetailTransaksi> items) {
        this.items = items;
    }
}
