package com.zte.buildabridge.object.runnable;

import android.os.Handler;

import com.zte.buildabridge.object.MessageInfo;
import com.zte.buildabridge.object.Population;

public class TranslationalMotion implements Runnable {
    
    private Handler handler;
    private Population population;

    public TranslationalMotion(Handler handler, Population population){
        this.handler = handler;
        this.population = population;
    }
    
    @Override
    public void run() {
        int a = population.getTranslationDistanceA();
        float b = population.getTranslationDistanceB();
        float multiple = b / (float) a;
        for (int i = 0; i <= a; i++) {
            population.translation(1, multiple);
            handler.sendEmptyMessage(MessageInfo.INVALIDATE);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        handler.sendEmptyMessage(MessageInfo.NEXT_LEVEL);
    }
}
