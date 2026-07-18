package com.mycompany.kasirkita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "pembeli")
public class Pembeli extends BaseEntity {

    @Column(name = "nama_pembeli", nullable = false)
    private String namaPembeli;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_kelamin", length = 1)
    private JenisKelamin jenisKelamin;

    @Column(name = "no_telepon", length = 20)
    private String noTelepon;

    @Column(name = "alamat")
    private String alamat;

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public JenisKelamin getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(JenisKelamin jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
