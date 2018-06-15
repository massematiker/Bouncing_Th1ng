package com.example.krebs.bouncing_th1ng;

import android.graphics.RectF;

public class Coin extends RectF {

    private RectF rect;

    private float length;
    private float height;

    private float x;
    private float y;

    private boolean isVisible;
    public RectF getRect(){
        return this.rect;
    }
    public void setInvisible(){
        isVisible = false;
    }
    public boolean getVisibility(){
        return isVisible;
    }

    /**
     * return the Lenght of the Coin
     * @return
     */
    public float getLength() {
        return length;
    }

    /**
     * return the height of the coin
     * @return
     */
    public float getHeight() {
        return height;
    }

    /**
     * set the height of the coin
     * @param height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * return the x coordinate of the coin
     * @return
     */
    public float getX() {
        return x;
    }

    /**
     * return the y coordinate of the coin
     * @return
     */
    public float getY() {
        return y;
    }

    /**
     * Constructor for a Coin object
     * @param screenX
     * @param screenY
     */
    public Coin(int screenX, int screenY){
        // 130 pixels wide and 20 pixels high
        length = 100;
        height = 100;

        x = (float) (screenX/((Math.random()*100%4.8)+1.2));
        y = (float) (screenY/((Math.random()*100%3.5)+1.4));

        isVisible=true;

        rect = new RectF(x, y, x + length, y + height);

    }
}
