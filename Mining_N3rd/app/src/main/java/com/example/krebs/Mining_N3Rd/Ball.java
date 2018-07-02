package com.example.krebs.Mining_N3Rd;

import android.graphics.RectF;

import java.util.Random;

public class Ball {
    RectF rect;
    float xVelocity;
    float yVelocity;
    float ballWidth = 20;
    float ballHeight = 20;


    public Ball(int screenX, int screenY){

        // Start the ball travelling straight up at 100 pixels per second
        xVelocity = 200;
        yVelocity = -400;

        // Place the ball in the centre of the screen at the bottom
        // Make it a 10 pixel x 10 pixel square
        rect = new RectF();

    }

    public RectF getRect(){
        return rect;
    }

    /**
     *
     * @param fps Frames per Secound to keep the moving constant
     */
    public void update(long fps){

        rect.left = rect.left + (xVelocity / fps);
        rect.top = rect.top + (yVelocity / fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top - ballHeight;
    }

    /**
     * Reverse the Y direction of the ball
     */
    public void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    /**
     * Reverse the X direction of the ball
     */
    public void reverseXVelocity(){
        xVelocity = - xVelocity;
    }
    /**
     * Set the Y velocity of the ball
     */
    public void setXVelocity(float xVelocity){
        this.xVelocity=xVelocity;
    }
    /**
     * Set the X velocity of the ball
     */
    public void setYVelocity(float yVelocity){
        this.yVelocity=yVelocity;
    }

    /**
     * Set the Y Direction of the ball to a defined position to prevent double collisions
     *
     * @param y the new Y Position of the Ball
     */
    public void clearObstacleY(float y){
        rect.bottom = y;
        rect.top = y - ballHeight;
    }

    /**
     * Set the X Direction of the ball to a defined position to prevent double collisions
     *
     * @param x the new X Position of the Ball
     */
    public void clearObstacleX(float x){
        rect.left = x;
        rect.right = x + ballWidth;
    }


    /**
     * Reset the X and Y coordinates to a defined position
     *
     * @param x     the new X Position of the Ball
     * @param y     the new Y Position of the Ball
     */
    public void reset(int x, int y){
        rect.left = x / 2;
        rect.top = y - 20;
        rect.right = x / 2 + ballWidth;
        rect.bottom = y - 20 - ballHeight;
    }

    //Ballgeschwindigkeit erhöhen um 10 Prozent

    /**
     * Increase the Speed of the Ball about 10 %
     */
    public void makeballFaster(){

            xVelocity = xVelocity * 1.1f ;
            yVelocity = yVelocity * 1.1f ;


    }

    public float getxVelocity() {
        return xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }


}