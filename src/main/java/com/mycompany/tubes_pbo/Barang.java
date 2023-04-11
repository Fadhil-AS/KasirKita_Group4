/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_pbo;

/**
 *
 * @author USER
 */
import java.util.Random;
public class Barang extends transaksi{
    public int Id_barang;
    
    public Barang(String nama, int quantity, double price, double totalbayar, int id_t, int id_barang){
        super(nama, quantity, price, totalbayar,id_t);
        this.Id_barang = id_barang;
    }
    

    public void detailbarang(){
        System.out.println("nama barang " + nama_barang + " memiliki sisa stok sebanyak " + getNewstok() + " dan harga totalnya " + getPrice());
    }
    
    
}
