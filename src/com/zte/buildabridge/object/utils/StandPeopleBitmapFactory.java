package com.zte.buildabridge.object.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.zte.buildabridge.R;

public class StandPeopleBitmapFactory {
    
    private static Bitmap stand;
    
    private StandPeopleBitmapFactory() {
        
    }
    
    public static Bitmap createBitmap(Context context, Size size) {
        if(stand == null){
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.stand);
            bitmap = BitmapUtils.transparent(bitmap);
            Matrix matrix = new Matrix();
            matrix.setScale((float)size.getWidth() / bitmap.getWidth(), (float)size.getHeight() / bitmap.getHeight());
            stand =  Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        
        return stand;
        
    }
}
