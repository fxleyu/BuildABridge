package com.zte.buildabridge.object.runnable;

import android.os.Handler;

import com.zte.buildabridge.object.MessageInfo;
import com.zte.buildabridge.object.Population;
import com.zte.buildabridge.object.State;

public class StartToRun implements Runnable{
    
    private Handler handler;
    private Population population;
    
    public StartToRun(Handler handler, Population population) {
        this.handler = handler;
        this.population = population;
    }


    @Override
    public void run() {
        population.setRunning(true);
        if (population.personSafe()) {
            int limit = population.personRunDistance();
            personRunning(limit);
            State.scoreIncrease();
            handler.sendEmptyMessage(MessageInfo.SUCCESS);
        } else {
            personRunning(population.getPlankLength());
            sleep(200); // 失败前暂停一小段时间
            handler.sendEmptyMessage(MessageInfo.PLANK_DOWN);
        }
        population.setRunning(false);
    }


    private void personRunning(int limit) {
        for (int step = 0; step < limit; step++) {
            population.setPersonStep(step);
            sleep(1);
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
