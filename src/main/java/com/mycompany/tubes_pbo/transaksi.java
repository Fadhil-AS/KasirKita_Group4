/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_pbo;

import java.time.LocalDateTime;

/**
 *
 * @author USER
 */
public abstract class transaksi {
   
    public int id_transaksi;
    public String nama_barang;
    public int quantity;
    private double price;
    public int stok;
    public LocalDateTime tgl_bayar;
    private double totalbayar;
    protected String logusername;
    
    
    
    public transaksi(String nama, int quantity, double price, double totalbayar, int id_t){
        this.quantity = quantity;
        this.nama_barang = nama;
        this.price = price;
        this.totalbayar = totalbayar;
        this.id_transaksi = id_t;
        
    }
    
    protected double getPrice(){
        double harga = quantity * price;
        return harga;
    }
    
    protected int getNewstok(){
        int jum = quantity-stok;
        return jum;
    }
    
    
    public void printdetail(){
        
    }
    
    
    public double kembalian(double totalbayar){
        double bayar = totalbayar - getPrice() ;
        return bayar;
    }
    public LocalDateTime tanggalbayar(){
        return tgl_bayar = LocalDateTime.now();
    }
    
    public void showfulldetailtransaksi(double tBayar){
        getPrice();
        getNewstok();
        kembalian(tBayar);
        tanggalbayar();
        printdetail();
    }
}
