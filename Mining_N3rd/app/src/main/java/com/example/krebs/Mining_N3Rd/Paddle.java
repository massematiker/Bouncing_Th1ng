package com.example.krebs.Mining_N3Rd;
import android.graphics.RectF;

public class Paddle {

    // RectF is an object that holds four coordinates - just what we need
    private RectF rect;

    // How long and high our paddle will be
    private float length;
    private float height;

    // X is the far left of the rectangle which forms our paddle
    private float x;

    // Y is the top coordinate
    private float y;

    // This will hold the pixels per second speed that the paddle will move
    private float paddleSpeed;

    // Which ways can the paddle move
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    // Is the paddle moving and in which direction
    private int paddleMoving = STOPPED;

    private int screenX;

    // This the the constructor method
    // When we create an object from this class we will pass
    // in the screen width and height
    public Paddle(int screenX, int screenY){
        // 130 pixels wide and 20 pixels high
        length = 250;
        //muss unten noch angepasst werden bei y
        height = 60;

        // Start paddle in roughly the sceen centre
        x = screenX / 2;
        //hier der Wert von Height
        y = screenY - 60 ;
        this.screenX = screenX;

        rect = new RectF(x, y, x + length, y + height);

        // How fast is the paddle in pixels per second
        paddleSpeed = 500;
    }


    /**
     * This is a getter method to make the rectangle that
     * defines our paddle available in BreakoutView class
     * @return
     */
    public RectF getRect(){
        return rect;
    }

    /**
     * This method will be used to change/set if the paddle is going left, right or nowhere
      */

    /**
     * set the Paddle Direction
     * @param state STOPPED = 0     LEFT = 1    RIGHT = 2
     */
    public void setMovementState(int state){
        paddleMoving = state;
    }

    /**
     * return X coordinate of the Paddle
     * @return
     */
    public float getX(){
        return x;
    }
    /**
     * return Y coordinate of the Paddle
     * @return
     */
    public float getY(){
        return y;
    }

    /**
     * return the Legth of the Paddle
     * @return
     */
    public float getlength(){
        return length;
    }

    /**
     * return the height of the paddle
     * @return
     */
    public float getheight(){
        return height;
    }

    /**
     * return the Paddel Moving State
     * @return STOPPED = 0     LEFT = 1    RIGHT = 2
     */
    public int getpaddleMoving(){
        return paddleMoving;
    }

    /**
     * return the speed of the paddle
     * @return
     */
    public float getPaddleSpeed() {
        return paddleSpeed;
    }

    /**
     * This update method will be called from update in BreakoutView
     * It determines if the paddle needs to move and changes the coordinates
     * contained in rect if necessary
     * @param fps
     */
    public void update(long fps){
        if(paddleMoving == LEFT && x > 0){
            x = x - paddleSpeed / fps;
        }

        if(paddleMoving == RIGHT  && x< screenX - length){
            x = x + paddleSpeed / fps;
        }

        rect.left = x;
        rect.right = x + length;
    }

    public void setPaddleSpeed(float paddleSpeed) {
        this.paddleSpeed = paddleSpeed;
    }
}