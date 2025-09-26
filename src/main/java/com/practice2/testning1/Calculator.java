package com.practice2.testning1;

public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }
    public int subtract(int a, int b) {
        return a - b;
    }
    public boolean isEven (int number) {
        return number % 2 == 0;
    }
    public double calculateTotal(double price, double taxRate) {
        return price + (price * taxRate);
    }
}
