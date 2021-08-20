package com.example.digicore;

public class PerfectSquareCount {
    public static int perfectSquares(int length, int breadth){

        int loop = length == breadth && length != 1? length - 1 : length > breadth? breadth: length;

        int sumOfSquares = 0;

        while(loop != 0){
            sumOfSquares += length * breadth;
            length--;
            breadth--;
            loop--;
        }

        return sumOfSquares;
    }

    public static void main(String[] args) {
        System.out.println(perfectSquares(4,5));
        System.out.println(perfectSquares(1,1));
        System.out.println(perfectSquares(3,3));
        System.out.println(perfectSquares(2,3));
        System.out.println(perfectSquares(2,4));
    }
}
