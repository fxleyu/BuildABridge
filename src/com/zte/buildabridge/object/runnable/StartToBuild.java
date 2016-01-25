package com.zte.buildabridge.object.runnable;

import com.zte.buildabridge.object.MessageInfo;
import com.zte.buildabridge.object.Population;

import android.os.Handler;

public class StartToBuild implements Runnable {

    private Handler handler;
    private Population population;
    private int degree;
    private int MAX_DEGREE = 90;

    public StartToBuild(Handler handler, Population population) {
        degree = 0;
        this.handler = handler;
        this.population = population;
    }

    public void init() {
        degree = 0;
    }

    @Override
    public void run() {
        population.lock();
        for (; degree <= MAX_DEGREE; degree++) {
            population.rotate(degree);
            handler.sendEmptyMessage(MessageInfo.INVALIDATE);
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        handler.sendEmptyMessage(MessageInfo.START_TO_RUN);
    }
}
