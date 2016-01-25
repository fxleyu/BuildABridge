package com.zte.buildabridge.view.handler;

import com.zte.buildabridge.object.MessageInfo;
import com.zte.buildabridge.object.Population;
import com.zte.buildabridge.view.GameMainView;

import android.os.Handler;
import android.os.Message;

public class GameHandler extends Handler {

    private GameMainView view;
    private Population population;

    public GameHandler(GameMainView view) {
        this.view = view;
    }
    
    public void setPopulation(Population population){
        this.population = population;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
        case MessageInfo.INVALIDATE: {
            view.invalidate();
            break;
        }
        case MessageInfo.START_TO_RUN: {
            population.startToRun();
            break;
        }
        case MessageInfo.PLANK_DOWN: {
            population.down();
            break;
        }
        case MessageInfo.SUCCESS: {
            population.translationalMotion();
            break;
        }
        case MessageInfo.NEXT_LEVEL: {
            population.nextLevel();
            break;
        }
        case MessageInfo.GAME_OVER: {
            view.gameOver();
            break;
        }
        default:
            break;
        }
    }

}
