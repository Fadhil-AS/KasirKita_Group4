/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_pbo;

/**
 *
 * @author USER
 */
public class supplier extends transaksi implements Login {
    public int id_supp;
    public String nama_supp;
    public int no_telp;
    public String alamat;

    public supplier(int id_supp, String nama_supp, int no_telp, String alamat, String nama, int quantity, double price, double totalbayar, int id_t, int id_bayar){
        super(nama, quantity, price, totalbayar,id_t);
        this.id_supp = id_supp;
        this.nama_supp = nama_supp;
        this.no_telp = no_telp;
        this.alamat = alamat;
    }

    public void tampilDataSupplier(){
        System.out.println("id: "+ id_supp);
        System.out.println("Nama Supplier: "+nama_supp);
        System.out.println("No.Telp: "+ no_telp);
        System.out.println("Alamat: "+ alamat);
    }
    
    @Override
    public void login(String username){
        String logusername = username;
    }
    
    @Override
    public void printdetail(){
        if(logusername == null){
            tampilDataSupplier();
        }else if(logusername != null && logusername.isEmpty()){
            System.out.println("Maaf anda silahkan Login terlebih dahulu untuk melihat detail supplier");
        }
    }
}

