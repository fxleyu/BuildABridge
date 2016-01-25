package com.zte.buildabridge.object.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BitmapUtils {
    
    /**
     * 
     * @param bitmap �����˱�����bitmap
     * @return bitmap �ѱ���ת��Ϊ͸��ɫ
     */
    public static Bitmap transparent(Bitmap bitmap) {
        Bitmap result = bitmap.copy(bitmap.getConfig(), true);
        
        result.setHasAlpha(true);
        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getHeight(); j++) {
                int color = result.getPixel(i, j);
                result.setPixel(i, j, color == -1117445 ? Color.TRANSPARENT
                        : Color.BLACK);
            }
        }
        
        return result;
    }
}
