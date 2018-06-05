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

    public float getWidth() {
        return width;
    }

    private float height;
    private int screenX;
    private int row;
    private int column;


    // Which ways can the obstacles move

    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    public final int UP = 3;
    public final int DOWN = 4;

    public void setObstacleMoving(int obstacleMoving) {
        this.obstacleMoving = obstacleMoving;
    }

    // Is the paddle moving and in which direction
    private int obstacleMoving = UP;

    private int obstacleSpeed = 100;

    public Obstacle(int row, int column, int width, int height){

        isVisible = true;
        int padding = 10;

        obstacleMoving = (int)((Math.random() * 100) % 3);


        this.column = column;
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
    public void setVisible(){ this.isVisible = true; }

    public boolean getVisibility(){
        return isVisible;
    }

    public int getObstacleMoving() {
        return obstacleMoving;
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

    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public float getHeight() {
        return height;
    }
}