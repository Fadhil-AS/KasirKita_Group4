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
public class pembeli extends transaksi implements Login{
    public String namaPembeli;
    public boolean jenis_kelamin;
    public int no_telepon;
    public String alamat;
    public int No_pembeli;
    
    
    public pembeli(String nama, int quantity, double price, double totalbayar, int id_t, String namapembeli, boolean jk, int no_hp, int no_pembeli, String alamat){
        super(nama, quantity, price, totalbayar,id_t);
        this.namaPembeli = namapembeli;
        this.jenis_kelamin = jk;
        this.no_telepon = no_hp;
        this.No_pembeli = no_pembeli;
        this.alamat = alamat;
    }
    
    /*public int getnoPembeli(){
        int no;
        Random rand = new Random();
        no = rand.nextInt(this.No_pembeli);
        return no;
    }*/
    
    @Override
    public void login(String username){
        logusername = username;
    }
    
    public String getjeniskelamin(){
        String L = "Laki-laki";
        String P = "Perempuan";
        if(jenis_kelamin == true){
            return L;
        }else{
            return P;
        }
    }
    
    public void detailpembeli(){
        System.out.println("No pembeli: " + No_pembeli);
        System.out.println("Nama Pembeli: " + namaPembeli);
        System.out.println("Jenis Kelamin: "+ getjeniskelamin());
        System.out.println("Nomer telepon: " + no_telepon);
        System.out.println("Alamat pembeli: "+ alamat);
    }
    
    @Override
    public void printdetail(){
        if(logusername == null){
            detailpembeli();
        }else if(logusername != null && logusername.isEmpty()){
            System.out.println("Maaf anda silahkan Login terlebih dahulu untuk melihat detail pembeli");
        }
    }
    
    
}
