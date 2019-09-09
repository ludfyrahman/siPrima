package com.android.primaitech.siprima.Config;

import java.util.Stack;

public class Stack_Menu {
    public static Stack<String> stack = new Stack<>();
    public void push(String val){
        stack.push(val);
    }
    public int count(){
        return stack.size();
    }
    public String hapusDataTeratas(){
        String cardAtTop = stack.pop();
        return cardAtTop;
    }
    public String TampilkanDataTeratas(){
        String cardAtTop = stack.peek();
        return cardAtTop;
    }
    public void hapus(){
        stack.clear();
    }
}
