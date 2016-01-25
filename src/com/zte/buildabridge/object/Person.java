package com.zte.buildabridge.object;

import com.zte.buildabridge.object.utils.Location;
import com.zte.buildabridge.object.utils.RunningPeopleBitmapFactory;
import com.zte.buildabridge.object.utils.Size;
import com.zte.buildabridge.object.utils.StandPeopleBitmapFactory;

import android.content.Context;
import android.graphics.Bitmap;

public class Person {

    private Bitmap stand;
    private RunningPeopleBitmapFactory runningFactory;
    private Location lcdLocation;
    private Location location;
    private int step;
    private Size size;
    private boolean isRunning;

    /**
     * 
     * @param context
     * @param location
     * @param size
     */
    public Person(Context context, Location lcdLocation, Size size) {
        stand = StandPeopleBitmapFactory.createBitmap(context, size);
        runningFactory = new RunningPeopleBitmapFactory(context, size);
        this.lcdLocation = lcdLocation.copy();
        this.size = size.copy();

        this.location = new Location(lcdLocation.getWidth() - size.getWidth(),
                lcdLocation.getHeight() - size.getHeight());
    }

    public Bitmap stand() {
        return stand;
    }
    
    public void setStep(int num){
        step = num;
        location.setWidth(lcdLocation.getWidth() - size.getWidth() + step);
    }

    public Bitmap step() {
        return runningFactory.step(step);
    }

    public Location getLocation() {
        return location;
    }

    public void downLocation(int degree) {
        location.setHeight(location.getHeight() + (degree-90)*2);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void setLocation(Location lcdLocation) {
        location = new Location(lcdLocation.getWidth() - size.getWidth(),
                lcdLocation.getHeight() - size.getHeight());
    }
}
