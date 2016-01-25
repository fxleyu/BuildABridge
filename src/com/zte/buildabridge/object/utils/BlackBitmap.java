package com.zte.buildabridge.object.utils;

import com.zte.buildabridge.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BlackBitmap {
    private Bitmap bitmap;

    public BlackBitmap(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.black);
    }

    public Bitmap createBitmap(Size size) {
        Matrix matrix = new Matrix();
        matrix.setScale((float) size.getWidth() / bitmap.getWidth(),
                (float) size.getHeight() / bitmap.getHeight());
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }
}
