package com.zte.buildabridge.object;

import android.content.Context;
import android.graphics.Bitmap;

import com.zte.buildabridge.object.utils.BlackBitmap;
import com.zte.buildabridge.object.utils.IDrawBitmap;
import com.zte.buildabridge.object.utils.Location;
import com.zte.buildabridge.object.utils.Size;

public class Platform implements IDrawBitmap{
    
    private Bitmap platform;
    private Location location;    
    private BlackBitmap black;
    
    public Platform(Context context, Location location, Size size ){
        black = new BlackBitmap(context);
        platform = black.createBitmap(size);
        this.location = location;
    }

    public Bitmap getBitmap() {
        return platform;
    }

    public void setPlatform(Bitmap platform) {
        this.platform = platform;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location lcdLocation) {
        location.setWidth(lcdLocation.getWidth() - platform.getWidth());
    }
}
