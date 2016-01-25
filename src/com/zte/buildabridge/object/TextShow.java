package com.zte.buildabridge.object;

import com.zte.buildabridge.setting.Configure;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.FontMetricsInt;

public class TextShow {

    private Paint paintText;
    private Paint paintText2;
    private Paint paintBackground;
    private RectF rectF; // 设置个新的长方形
    private float baseLine;

    public TextShow() {
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(60);
        paintText.setTextAlign(Paint.Align.CENTER);

        paintText2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText2.setColor(Color.BLACK);
        paintText2.setTextSize(30);
        paintText2.setTextAlign(Paint.Align.CENTER);

        paintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBackground.setColor(Color.DKGRAY);

        rectF = new RectF(20, 75, Configure.LCD_WIDTH - 20, 175);// 设置个新的长方形
        FontMetricsInt fontMetrics = paintText.getFontMetricsInt();
        baseLine = rectF.top
                + (rectF.bottom - rectF.top - fontMetrics.bottom + fontMetrics.top)
                / 2 - fontMetrics.top;
    }

    public void draw(Canvas canvas) {
        canvas.drawRoundRect(rectF, 20, 15, paintBackground);//
        canvas.drawText(State.getScore() + "", rectF.centerX(), baseLine,
                paintText);

        if (State.getScore() < 3) {
            canvas.drawText("手指按住屏幕,竿会变长或变短", rectF.centerX(), 220, paintText2);
            canvas.drawText("不可否认这是个时间杀手", rectF.centerX(), 270, paintText2);
            canvas.drawText("请不要上瘾哦", rectF.centerX(), 320, paintText2);

        }
    }
}
