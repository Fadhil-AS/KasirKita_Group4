/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tubes_pbo;

/**
 *
 * @author USER
 */
public class Tubes_PBO {

    public static void main(String[] args) {
        System.out.println("data pembeli");
        pembeli p1 = new pembeli("indomie", 2, 10000.0, 20000.0, 12121, "risky", true, 12312312, 12312, "bandung");
        p1.detailpembeli();
        
        System.out.println("---------------------------");
        
        System.out.println("data pembayaran");
        Pembayaran t1 = new Pembayaran("Indomie",2,10000.0,50000.0,1231,132144);
        t1.printdetail();
        
        System.out.println("---------------------------");
        
        System.out.println("data barang");
        Barang b1 = new Barang("Indomie",2,10000.0,50000.0,1231,123123);
        b1.detailbarang();
        
        System.out.println("---------------------------");
        
        System.out.println("data supplier");
        supplier s1 = new supplier(213131, "farhan", 123123, "bandung","Indomie",2,10000.0,50000.0,1231,2343234);
        s1.printdetail();
        
    }
}
