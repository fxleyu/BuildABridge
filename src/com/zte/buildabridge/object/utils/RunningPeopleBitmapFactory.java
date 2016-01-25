package com.zte.buildabridge.object.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.zte.buildabridge.R;

public class RunningPeopleBitmapFactory {
    private static Bitmap[] running;

    public RunningPeopleBitmapFactory(Context context, Size size) {
        running = new Bitmap[4];

        Bitmap step1 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.run01);
        Bitmap step2 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.run02);
        Bitmap step3 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.run03);
        Bitmap step4 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.run04);
        step1 = BitmapUtils.transparent(step1);
        step2 = BitmapUtils.transparent(step2);
        step3 = BitmapUtils.transparent(step3);
        step4 = BitmapUtils.transparent(step4);

        Matrix matrix = new Matrix();
        matrix.setScale((float) size.getWidth() / step1.getWidth(),
                (float) size.getHeight() / step2.getHeight());

        running[0] = Bitmap.createBitmap(step1, 0, 0, step2.getWidth(),
                step2.getHeight(), matrix, true);
        running[1] = Bitmap.createBitmap(step2, 0, 0, step2.getWidth(),
                step2.getHeight(), matrix, true);
        running[2] = Bitmap.createBitmap(step3, 0, 0, step2.getWidth(),
                step2.getHeight(), matrix, true);
        running[3] = Bitmap.createBitmap(step4, 0, 0, step2.getWidth(),
                step2.getHeight(), matrix, true);
    }

    public Bitmap step(int index) {
        return running[index % 4];

    }
}
