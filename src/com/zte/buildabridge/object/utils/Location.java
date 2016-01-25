package com.zte.buildabridge.object.utils;

public class Location {
    
    private float width;
    private float height;
    
    public Location(float width, float height){
        this.setWidth(width);
        this.setHeight(height);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    
    public void decreaseWidth(float dec){
        width -= dec;
    }

    public Location copy() {
        return new Location(width, height);
    }

    @Override
    public String toString() {
        return "Location [width=" + width + ", height=" + height + "]";
    }
}
