package com.zte.buildabridge.object.utils;

public class Size {
    private int width;
    private int height;

    /**
     * 物体在屏幕中的大小
     * @param width
     * @param height
     */
    public Size(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Size copy() {
        return new Size(width, height);
    }
    
}
