package com.example.krebs.Mining_N3Rd;

import android.graphics.RectF;

public class Boost {

    //1 Energy      //2 more Points    //3 Obstacles invisible
    int typ = 0;

    private RectF rect;

    private float length;
    private float height;

    private float x;

    private float y;
    private int boostTime ;
    private boolean isVisible;


    public Boost(int screenX, int screenY){
        // 130 pixels wide and 20 pixels high

        // Height and Lenght of the Boost´s. If you change this please note that you also have to change the same one´s for the pictures (boostpic1,2,3)
        //1 Paddle faster -  clubmate     //2 more points - Raspberry     //3 obstacles invisible - Mouse
        typ = (int) ((Math.random()*100%3)+1);
        switch (typ){
            case 1: height = 190;
                    length = 50;
                    break;
            case 2: height = 90;
                    length = 140;
                    break;
            case 3: height = 100;
                    length = 180;
                    break;
        }
        x = (float) (screenX/((Math.random()*100%4.8)+1.2));
        y = (float) (screenY/((Math.random()*100%2.6)+1.4));
        boostTime=0;
        isVisible=false;

        rect = new RectF(x, y, x + length, y + height);

    }

    /**
     * Return the rect of the boost
     * @return
     */
    public RectF getRect(){
        return this.rect;
    }

    /**
     * set the Boost invisible
     */
    public void setInvisible(){
        isVisible = false;
    }
    /**
     * set the Boost visible
     */
    public void setVisible(){
        isVisible = true;
    }

    /**
     * return visiblity of the boost
     * @return
     */
    public boolean getVisibility(){
        return isVisible;
    }

    /**
     * return the time the boost is still visible
     * @return
     */
    public int getBoostTime() {
        return boostTime;
    }

    /**
     * Set the time the boost is still visible
     * @param boosttime     time the boost is visible
     */
    public void setBoostTime(int boosttime) {
        this.boostTime = boosttime;
    }

    /**
     * return the height of the boost
     * @return
     */
    public float getHeight() {
        return height;
    }

    /**
     * set the height of the boost
     * @param height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * return the x coordinate of the boost
     * @return
     */
    public float getX() {
        return x;
    }


    /**
     * return the y coordinate of the boost
     * @return
     */
    public float getY() {
        return y;
    }

    /**
     * return the Type of the Boost
     *
     * @return 1 = Energy      2= more Points    3 = Obstacles invisible
     */
    public int getTyp() {
        return typ;
    }



}
