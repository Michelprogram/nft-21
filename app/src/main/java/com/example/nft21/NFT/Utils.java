package com.example.nft21.NFT;

import java.util.Random;

public class Utils {

    public static Double randomEthPrice(){
        Double MAX = 3.0, MIN = 1.10;
        Double result = (Math.random() * (MAX - MIN) + 1) + MIN;

        String tempo = String.format("%.2f",result);

        return Double.parseDouble(tempo);
    }

    public static Boolean mostViewed(){
        Integer MAX = 5;
        Integer result = (int)(Math.random() * MAX);

        return result == MAX - 1;
    }

    public static Double convertEthToEuro(){
        return 0.0;
    }

    public static Double convertEuroToEth(){
        return 0.0;
    }

}
