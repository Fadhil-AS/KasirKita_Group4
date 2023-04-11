/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_pbo;

/**
 *
 * @author USER
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Pembayaran extends transaksi implements Login{
    
    public LocalDateTime tgl_bayar;
    private double totalbayar;
    public int id_pembayaran;
    
    
    public Pembayaran(String nama, int quantity, double price, double totalbayar, int id_t, int id_bayar){
        super(nama, quantity, price, totalbayar,id_t);
        this.totalbayar = totalbayar;
        this.id_pembayaran = id_bayar;
    }
    
    @Override
    public double kembalian(double totalbayar){
        double bayar = getPrice() - totalbayar;
        return bayar;
    }
    public LocalDateTime tanggalbayar(){
        return tgl_bayar = LocalDateTime.now();
    }
    
    
    public void detailtransaksi(){
        
        System.out.println("No bayar: " + id_pembayaran);
        System.out.println("Barang Yang dibeli: " + nama_barang);
        
        System.out.println("Jumlah Barang: " + quantity);
        System.out.println("Total Harga: " + getPrice());
        System.out.println("Uang yang dibayar: " + totalbayar);
        System.out.println("Kembalian: "+ kembalian(totalbayar));
        System.out.println("Tanggal pembayaran: " + tanggalbayar());
    }
    
    @Override
    public void login(String username){
        String logusername = username;
    }
    @Override
    public void printdetail(){
        if(logusername == null){
            detailtransaksi();
        }else if(logusername != null && logusername.isEmpty()){
            System.out.println("Maaf anda silahkan Login terlebih dahulu untuk melihat detail pembayaran");
        }
    }
}
