package com.zte.buildabridge.object;

import com.zte.buildabridge.object.utils.BlackBitmap;
import com.zte.buildabridge.object.utils.IDrawBitmap;
import com.zte.buildabridge.object.utils.Location;
import com.zte.buildabridge.object.utils.Size;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Plank implements IDrawBitmap{

    final public static int WIDTH = 5;
    final public static int DEFAULT_HEIGHT = 1;
    
    private Bitmap plank;
    private Location lcdLocation;
    private Location location;
    private Size size;

    private int degree;
    private boolean isGrow;
    private boolean isLock;
    private Bitmap tempPlank;
    private BlackBitmap black;
    private int limit; // 木板长度限制

    /**
     * 
     * @param context
     *            Plank所在上下文环境
     * @param limit
     *            plank长度限制
     * @param location
     *            plank在上下文中的固定位置（也是左下角的位置）
     */
    public Plank(Context context, int limit, Location location) {
        black = new BlackBitmap(context);
        size = new Size(WIDTH, DEFAULT_HEIGHT);
        plank = black.createBitmap(size);
        tempPlank = plank;
        this.limit = limit;
        lcdLocation = location.copy();
        this.location = new Location(lcdLocation.getWidth(),
                lcdLocation.getHeight() - plank.getHeight());
        //Log.d(Tag.TAG_081811, "plank init location "+location.toString());
    }

    /**
     * plank自动变化长度的函数
     */
    public void change() {
        if (plank.getHeight() > limit) {
            isGrow = false;
        } else if (plank.getHeight() <= 1) { // plank 长度要大于0，所以最小为1
            isGrow = true;
        }

        size.setHeight(size.getHeight()+(isGrow ? 1 : -1));  
        plank = black.createBitmap(size);
        tempPlank = plank;
        updateLocation();
    }

    private void updateLocation() {
        if(degree == 0){
            location.setHeight(lcdLocation.getHeight() - plank.getHeight());
        }else if(degree < 90){
            location.setHeight(lcdLocation.getHeight() - plank.getHeight()
                    + (float) (WIDTH * Math.sin(Math.PI * degree / 180))); // 更新Plank的位置位置
        }else{
            location.setHeight(lcdLocation.getHeight()+WIDTH-(float) (WIDTH * Math.cos(Math.PI * (degree-90) / 180))); // 更新Plank的位置位置
        }
        
    }

    @Override
    public Bitmap getBitmap() {
        plank.setHasAlpha(true);
        return plank;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean isLock) {
        this.isLock = isLock;
    }

    /**
     * 竖直的plank旋转degree度
     * 
     * @param degree
     *            旋转度数
     */
    public void rotate(int degree) {
        this.degree = degree;
        Matrix matrix = new Matrix();
        matrix.preRotate(degree, tempPlank.getWidth(), tempPlank.getHeight());
        plank = Bitmap.createBitmap(tempPlank, 0, 0, tempPlank.getWidth(),
                tempPlank.getHeight(), matrix, true);
        updateLocation();
    }

    /**
     * 获取plank在LCD中的位置
     * 
     * @return plank在坐标系中的位置
     */
    @Override
    public Location getLocation() {
        return location;
    }
}
