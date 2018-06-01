package com.example.krebs.bouncing_th1ng;

import android.graphics.RectF;

public class Obstacle {

    private RectF rect;

    private boolean isVisible;

    private float left;
    private float top;
    private float bottom;
    private float right;
    private float width;
    private float height;

    private int screenX;

    // Which ways can the paddle move
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    public final int UP = 3;
    public final int DOWN = 4;

    // Is the paddle moving and in which direction
    private int obstacleMoving = UP;

    private int obstacleSpeed = 100;

    public Obstacle(int row, int column, int width, int height){

        isVisible = true;
        int padding = 1;

        obstacleMoving = (int)((Math.random() * 100) % 5);


        left=column * width + padding;
        top= row * height + padding;
        right =column * width + width - padding;
        bottom = row * height + height - padding;

        this.width  = width;
        this.height = height;
        this.screenX = width *8;

        rect = new RectF(left,
                top,
                right,
                bottom);
    }

    public RectF getRect(){
        return this.rect;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }

    public void update(long fps){

        float length = right -left;

        if(obstacleMoving == LEFT ){
            left = left - obstacleSpeed / fps;
            right = right - obstacleSpeed / fps;
        }

        if(obstacleMoving == RIGHT ){
            left = left + obstacleSpeed / fps;
            right = right + obstacleSpeed / fps;
        }

        if(obstacleMoving == DOWN){
            top = top + obstacleSpeed / fps;
            bottom = bottom + obstacleSpeed / fps;
        }

        if(obstacleMoving == UP){
            top = top - obstacleSpeed / fps;
            bottom = bottom - obstacleSpeed / fps;
        }

        rect.left = left;
        rect.right = right;
        rect.top = top;
        rect.bottom = bottom;
        System.out.println(rect);
        // rect.right = left + length;
    }

    public void changeDirection(){
        switch(obstacleMoving){
            case STOPPED: break;
            case LEFT:  obstacleMoving = RIGHT;
                        break;
            case RIGHT: obstacleMoving = LEFT;
                        break;
            case UP:    obstacleMoving = DOWN;
                        break;
            case DOWN:  obstacleMoving = UP;
                        break;
        }
    }
}