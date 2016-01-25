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
    private int limit; // ľ�峤������

    /**
     * 
     * @param context
     *            Plank���������Ļ���
     * @param limit
     *            plank��������
     * @param location
     *            plank���������еĹ̶�λ�ã�Ҳ�����½ǵ�λ�ã�
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
     * plank�Զ��仯���ȵĺ���
     */
    public void change() {
        if (plank.getHeight() > limit) {
            isGrow = false;
        } else if (plank.getHeight() <= 1) { // plank ����Ҫ����0��������СΪ1
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
                    + (float) (WIDTH * Math.sin(Math.PI * degree / 180))); // ����Plank��λ��λ��
        }else{
            location.setHeight(lcdLocation.getHeight()+WIDTH-(float) (WIDTH * Math.cos(Math.PI * (degree-90) / 180))); // ����Plank��λ��λ��
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
     * ��ֱ��plank��תdegree��
     * 
     * @param degree
     *            ��ת����
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
     * ��ȡplank��LCD�е�λ��
     * 
     * @return plank������ϵ�е�λ��
     */
    @Override
    public Location getLocation() {
        return location;
    }
}
