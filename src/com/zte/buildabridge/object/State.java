package com.zte.buildabridge.object;

public class State {
    private static int score;
    
    public static void scoreIncrease(){
        score++;
    }
    

    public static int getScore(){
        return score;
    }


    public static void init() {
        score = 0;
    }
}
