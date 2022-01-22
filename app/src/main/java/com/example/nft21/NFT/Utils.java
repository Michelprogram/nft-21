package com.example.nft21.NFT;

import java.util.Random;

public class Utils {

    public static Double randomEthPrice(){
        Double MAX = 3.0, MIN = 1.10;
        Double result = (Math.random() * (MAX - MIN) + 1) + MIN;

        String tempo = String.format("%.2f",result);

        return Double.parseDouble(tempo);
    }

    public static Integer mostViewed(){
        Integer MAX = 3;
        Integer result = (int)(Math.random() * MAX);

        return (result == MAX - 1) ? 1 : 0;
    }

}

