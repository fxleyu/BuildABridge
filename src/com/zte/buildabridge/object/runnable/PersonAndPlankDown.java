package com.zte.buildabridge.object.runnable;

import com.zte.buildabridge.object.MessageInfo;
import com.zte.buildabridge.object.Population;

import android.os.Handler;

public class PersonAndPlankDown implements Runnable {
    
    private Handler handler;
    private Population population;

    public PersonAndPlankDown(Handler handler, Population population){
        this.handler = handler;
        this.population = population;
    }
    
    @Override
    public void run() {
        for (int degree = 90; degree <= 180; degree++) {
            population.down(degree);
            handler.sendEmptyMessage(MessageInfo.INVALIDATE);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        handler.sendEmptyMessage(MessageInfo.GAME_OVER);
    }

}
