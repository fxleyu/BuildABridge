package com.zte.buildabridge.object.runnable;

import android.os.Handler;

import com.zte.buildabridge.object.MessageInfo;
import com.zte.buildabridge.object.Population;

public class PlankChange implements Runnable {
    
    private Handler handler;
    private Population population;
    
    public PlankChange(Handler handler, Population population) {
        this.handler = handler;
        this.population = population;
    }
    
    @Override
    public void run() {
        while(population.hasChangePlank()){
            sleep(1);
            population.changePlank();
            handler.sendEmptyMessage(MessageInfo.INVALIDATE);
        }
    }
    
    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
