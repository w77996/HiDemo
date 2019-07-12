package com.w77996.exception;

public class Main {
    public static void main(String[] args) {
        ExInterface exComsumer = new ExComsumer();
        try {
            exComsumer.batch();
        } catch (Exception e) {
            System.out.println("dfsdf");
        }
    }
}
