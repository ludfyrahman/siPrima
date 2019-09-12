package com.primagroup.primaitech.siprima.Config;

import java.util.Stack;

public class Stack_Menu {
    public static Stack<String> kode_menu = new Stack<>();
    public static Stack<String> nama_menu = new Stack<>();
    public static void pushKodemenu(String val){
        kode_menu.push(val);
    }
    public static int jumlahKodeMenu(){
        return kode_menu.size();
    }
    public static int jumlahNamaMenu(){
        return nama_menu.size();
    }
    public static String hapusKodeMenuTeratas(){
        String cardAtTop = kode_menu.pop();
        return cardAtTop;
    }
    public static String TampilkanKodeMenuTeratas(){
        String cardAtTop = kode_menu.peek();
        return cardAtTop;
    }
    public void hapusKodeMenu(){
        kode_menu.clear();
    }
    public static void pushNamamenu(String val){
        nama_menu.push(val);
    }
    public static int countNamaMenu(){
        return nama_menu.size();
    }
    public static String hapusNamaMenuTeratas(){
        String cardAtTop = nama_menu.pop();
        return cardAtTop;
    }
    public static String TampilkanNamaMenuTeratas(){
        String cardAtTop = nama_menu.peek();
        return cardAtTop;
    }
    public void hapusNamaMenu(){
        nama_menu.clear();
    }
}
