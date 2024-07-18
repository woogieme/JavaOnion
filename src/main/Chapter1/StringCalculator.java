package main.Chapter1;

public class StringCalculator {
    int add(int i, int j){
        return i+j;
    }

    int subtract(int i, int j){
        return i-j;
    }

    int multiply(int i, int j){
        return i*j;
    }

    int divide(int i, int j){
        return i/j;
    }

    public static void main(String[] args) {
        StringCalculator stringCal = new StringCalculator();
        System.out.println(stringCal.add(3,4));
        System.out.println(stringCal.subtract(5,4));
        System.out.println(stringCal.multiply(2,6));
        System.out.println(stringCal.divide(8,4));
    }
}
