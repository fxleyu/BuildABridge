package com.zte.buildabridge.object.utils;

import java.util.Random;

import com.zte.buildabridge.setting.Configure;

public class GameLayout {

    final private int WIDTH_MIN = 8;
    final private int MAX_FLUCTUATE = 16;
    final private int BASE_NUM = 100;
    final private int BASE_POINT_X = 13;
    final private int BORDER = 3;

    private int lcdWidth;
    private int lcdHeight;
    private int interval;
    private int platformWidth;

    private Random random = new Random();

    public GameLayout(LCD lcd) {
        lcdWidth = lcd.getWidth();
        lcdHeight = lcd.getHeight();
        Configure.LCD_HEIGHT = lcdHeight;
        Configure.LCD_WIDTH = lcdWidth;
        initConfig();
    }

    private void initConfig() {
        Configure.BASE_POINT_X = (int) (lcdWidth * 0.13); // ����X����
        Configure.BASE_POINT_Y = (int) (lcdHeight * 0.67); // ����Y����
        Configure.BORDER_WIDTH = (int) (lcdWidth * 0.03); // �߽�
        Configure.PERSON_WIDTH = (int) (lcdWidth * 0.04); // ����˵Ŀ��
        Configure.PERSON_HEIGHT = (int) (lcdHeight * 0.03); // ����˵ĸ߶�
        Configure.PLATFORM_WIDTH_MAX = (int) (lcdWidth * 0.2); // ƽ̨��󳤶�
        Configure.PLATFORM_WIDTH_MIN = (int) (lcdWidth * 0.08); // ƽ̨��С����
        Configure.PLANK_MAX = (int) (lcdWidth * 0.85); // ����󳤶�
        Configure.PLANK_CHANGE_SPEED = 5;
    }

    public void init() {
        interval = (int) (lcdWidth * 0.38);
        platformWidth = (int) (lcdWidth * 0.13);
    }

    public void random() {
        int increase = random.nextInt(MAX_FLUCTUATE);
        int temp = increase + WIDTH_MIN;
        platformWidth = (int) (lcdWidth * temp / BASE_NUM);
        int fluctuate = BASE_NUM - BORDER - BASE_POINT_X - 2 * temp;
        
        increase = random.nextInt(fluctuate);
        interval = (int) (lcdWidth * (temp + increase) / BASE_NUM);
    }

    public int getInterval() {
        return interval;
    }

    public int getPlatformWidth() {
        return platformWidth;
    }

}
