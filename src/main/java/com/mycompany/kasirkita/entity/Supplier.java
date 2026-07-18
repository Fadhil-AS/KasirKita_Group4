package com.mycompany.kasirkita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier extends BaseEntity {

    @Column(name = "nama_supp", nullable = false)
    private String namaSupp;

    @Column(name = "no_telp", length = 20)
    private String noTelp;

    @Column(name = "alamat")
    private String alamat;

    public String getNamaSupp() {
        return namaSupp;
    }

    public void setNamaSupp(String namaSupp) {
        this.namaSupp = namaSupp;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
