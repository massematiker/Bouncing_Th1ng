package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.io.IOException;

import static android.graphics.Bitmap.createScaledBitmap;

// Imports for HighScore
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import android.content.SharedPreferences;

public class BouncingGame extends Activity implements SensorEventListener {

    // gameView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    BouncingView bouncingView;
    long starttime = 0;
    int playtime = 0;
    // Counter für Sekunden bis ein boost angezeigt wird muss auch in colliding boost geändert werden
    int noboost = 5;
    // Wie lange (ungefähr) ist ein boost sichtbar ?
    int boostsichtbar = 20;
    // Wie lange ist man geboostet //auch in sekunden zähler ändern
    int boostedtime1=10;
    int boostedtime2=10;
    int boostedtime3=10;
    // Boostscore
    int boostscore=0;


    boolean boosted1=false;
    boolean boosted2=false;
    boolean boosted3=false;

    
    boolean touch = true;

    // Create the Sensors
    private Display mDisplay;
    private WindowManager mWindowManager;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    int difficulty =1;

    //variables for the HighScore
    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        starttime = System.currentTimeMillis();

        // Initialize gameView and set it as the view
        bouncingView = new BouncingView(this);

        // Initialize the Sensors
        mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Initilization for the HighScore
        gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        touch = gamePrefs.getBoolean("touch",false);

        setContentView(bouncingView);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;

        if(touch) {
            // Hier wird der Sensor abgefragt und die X-Achse entsprechend verändert. Durch vz=0 kann das Trackpas nicht nach oben oder unten
            switch (mDisplay.getRotation()) {
                case Surface.ROTATION_0:
                    bouncingView.setPaddleMove(-event.values[0]);
                    break;
                case Surface.ROTATION_90:
                    bouncingView.setPaddleMove(event.values[1]);
                    break;
                case Surface.ROTATION_180:
                    bouncingView.setPaddleMove(event.values[0]);
                    break;
                case Surface.ROTATION_270:
                    bouncingView.setPaddleMove(-event.values[1]);
                    ;
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    // Here is our implementation of GameView
    // It is an inner class.
    // Note how the final closing curly brace }
    // is inside SimpleGameEngine

    // Notice we implement runnable so we have
    // A thread and can override the run method.
    class BouncingView extends SurfaceView implements Runnable {

        Bitmap ballpic;
        Bitmap ballpicblue;
        Bitmap background;
        Bitmap paddlepic;
        Bitmap paddlepicblue;
        Bitmap coinpic;
        Bitmap coinpicblue;
        Bitmap boostpic1;
        Bitmap boostpic2;
        Bitmap boostpic3;
        Bitmap obstaclepic;
        Bitmap destroyableobstaclepic;
        Bitmap keyboardbluepic;

        //booleans for the ball direction
        boolean ballAlternateDirection = false;

        // This is our thread
        Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        // A boolean which we will set and unset
        // when the game is running- or not.
        volatile boolean playing;

        // Game is paused at the start
        boolean paused = true;

        // A Canvas and a Paint object
        Canvas canvas;
        Paint paint;


        // This variable tracks the game frame rate
        long fps;

        // This is used to help calculate the fps
        private long timeThisFrame;

        // The size of the screen in pixels
        int screenX;
        int screenY;

        // The player's paddle
        Paddle paddle;

        // A ball
        Ball ball;

        // Coins erzeugen
        // Anzahl coins
        int numcoins = 3;
        Coin[] coins = new Coin[numcoins];


        // Boost erzeugen
        Boost boost ;


        // Up to 200 obstacles
        Obstacle[] obstacles = new Obstacle[200];
        int numObstacles = 0;

        // For sound FX
        SoundPool soundPool;

        int explodeID = -1;

        //new Sounds            //richtige stelle   sound drin
        int flaschensound = -1;    //done            done
        int mouse = -1;       //done            --
        int rechnen = -1;       //done            done
        int geld = -1;          //done            done
        int loseLifeID = -1;    //done            done
        int gameoverdeath =-1;  //done            done
        int obstaclesound = -1;
        int paddlesound = -1;



        // The score
        int score = 0;

        int timer = 1;
        int timeranzeige = 3;

        // Lives
        int lives = 3;

        public void setPaddleMove(float move){
            if(move <0.4 && move >-0.4){
                paddle.setMovementState(0);
            }
            else if(move<0) {
                move = -move;
                paddle.setMovementState(1);
                double speed = move/2 * paddle.getPaddleSpeed();
                if(speed >450 && !boosted1) speed = 450;
                if(speed >800 && boosted1) speed = 800;
                paddle.setPaddleSpeed((float)speed);
            }
            else {
                paddle.setMovementState(2);
                double speed = move/2 * paddle.getPaddleSpeed();
                if(speed >450 && !boosted1) speed = 450;
                if(speed >800 && boosted1) speed = 800;
                paddle.setPaddleSpeed((float)speed);
            }
        }

        // When the we initialize (call new()) on gameView
        // This special constructor method runs
        public BouncingView(Context context) {
            // The next line of code asks the
            // SurfaceView class to set up our object.
            // How kind.
            super(context);

            // Bitmap initialisieren
            ballpic = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
            ballpicblue = BitmapFactory.decodeResource(getResources(), R.drawable.pacmanblue);
            background = BitmapFactory.decodeResource(getResources(), R.drawable.gameboy);
            paddlepic = BitmapFactory.decodeResource(getResources(), R.drawable.keyboardred);
            paddlepicblue = BitmapFactory.decodeResource(getResources(), R.drawable.keyboardblue);
            coinpic = BitmapFactory.decodeResource(getResources(), R.drawable.bitcoin);
            coinpicblue = BitmapFactory.decodeResource(getResources(), R.drawable.bitcoinblue);
            boostpic1 = BitmapFactory.decodeResource(getResources(), R.drawable.clubmate);
            boostpic2 = BitmapFactory.decodeResource(getResources(), R.mipmap.rasperry);
            boostpic3 = BitmapFactory.decodeResource(getResources(), R.drawable.logitech);
            obstaclepic = BitmapFactory.decodeResource(getResources(), R.drawable.ps4);
            destroyableobstaclepic = BitmapFactory.decodeResource(getResources(), R.drawable.xbox);


            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();

            // Get a Display object to access screen details
            Display display = getWindowManager().getDefaultDisplay();
            // Load the resolution into a Point object
            Point size = new Point();
            display.getSize(size);

            screenX = size.x;
            screenY = size.y;

            // Create a Paddle
            paddle = new Paddle(screenX, screenY);
            // Create a ball
            ball = new Ball(screenX, screenY);

            //create a coin
            coins[0] = new Coin(screenX,screenY);
            coins[1] = new Coin(screenX,screenY);
            coins[2] = new Coin(screenX,screenY);

            //create boost
            boost = new Boost(screenX,screenY);


            // Load the sounds

            // This SoundPool is deprecated but don't worry
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);

            try{
                // Create objects of the 2 required classes
                AssetManager assetManager = context.getAssets();
                AssetFileDescriptor descriptor;

                // Load our fx in memory ready for use
                descriptor = assetManager.openFd("keyboard.ogg");
                paddlesound = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("explode.ogg");
                explodeID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("obstacle.ogg");
                obstaclesound = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("doh.ogg");
                loseLifeID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("flaschensound.ogg");
                flaschensound = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("cashregister.ogg");
                geld = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("gameover.ogg");
                gameoverdeath = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("typewriter.ogg");
                rechnen = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("click.ogg");
                mouse = soundPool.load(descriptor, 0);

            }catch(IOException e){
                // Print an error message to the console
                Log.e("error", "failed to load sound files");
            }

            createObstaclesAndRestart();
        }

        public void createObstaclesAndRestart(){

            // Put the ball back to the start
            ball.reset(screenX, screenY);

            int obstacleWidth = screenX / 8;
            int obstacleHeight = screenY / 20;

            // Build a wall of bricks
            numObstacles = 0;
            int destObstacles = 0;
            int AnzObstacles = 0;

            for(int column = 0; column < 8; column ++ ){
                for(int row = 2; row < 12; row ++ ){

                    if(row%2 == 0){
                        // Create Obstacles
                        if(Math.random()*100 < 75 || destObstacles>3){
                            obstacles[numObstacles] = new Obstacle(row, column, obstacleWidth, obstacleHeight);
                            obstacles[numObstacles].setRow(row);
                        }
                        else if (destObstacles<=3){// Create Destroyable Obstacles
                            obstacles[numObstacles] = new DestroyableObstacle(row, column, obstacleWidth, obstacleHeight);
                            obstacles[numObstacles].setRow(row);
                        }
                        //wenn aus 50 eine höhere zahl gemacht wird werden es weniger obstacles Felix trautmann
                        if(Math.random()*100 < 75){

                            obstacles[numObstacles].setInvisible();

                        }
                        else{
                            if(obstacles[numObstacles] instanceof DestroyableObstacle) destObstacles++;
                            else AnzObstacles++;
                        }

                        numObstacles ++;
                    }
                }
            }

            // minimum 5 undestroyable obstacles
            while(AnzObstacles<=3){
                int i =(int)((Math.random()*100)%numObstacles);
                if(!obstacles[i].getVisibility()  && !(obstacles[i] instanceof DestroyableObstacle)){
                    obstacles[i].setVisible();
                    AnzObstacles++;
                }
            }
            //max 6 undestroyable obstacles
            while(AnzObstacles>4){
                int i =(int)((Math.random()*100)%numObstacles);
                if(obstacles[i].getVisibility()  && !(obstacles[i] instanceof DestroyableObstacle)){
                    obstacles[i].setInvisible();
                    AnzObstacles--;
                }
            }
            // minimum 3 undestroyable obstacles
            while(destObstacles<=2){
                int i =(int)((Math.random()*100)%numObstacles);
                if(!obstacles[i].getVisibility()  ){
                    obstacles[i] = new DestroyableObstacle(obstacles[i].getRow(),obstacles[i].getColumn(),(int)obstacles[i].getWidth(),(int)obstacles[i].getHeight());
                    obstacles[i].setVisible();
                    destObstacles++;
                }
            }
            // max 3 undestroyable obstacles
            while(destObstacles>3){
                int i =(int)((Math.random()*100)%numObstacles);
                if(obstacles[i].getVisibility() && obstacles[i] instanceof DestroyableObstacle ){
                    obstacles[i].setInvisible();
                    destObstacles--;
                }
            }
            // Reset scores and lives
            score = 0;
            lives = 3;
        }//createObstaclesAndRestart

        public void createnewObstacles(){

            int obstacleWidth = screenX / 8;
            int obstacleHeight = screenY / 20;

            // Build a wall of bricks
            numObstacles = 0;
            int destObstacles = 0;
            int AnzObstacles = 0;

            for(int column = 0; column < 8; column ++ ){
                for(int row = 2; row < 12; row ++ ){

                    if(row%2 == 0){
                        // Create Obstacles
                        if(Math.random()*100 < 75 || destObstacles>3){
                            obstacles[numObstacles] = new Obstacle(row, column, obstacleWidth, obstacleHeight);
                            obstacles[numObstacles].setRow(row);
                        }
                        else if (destObstacles<=3){// Create Destroyable Obstacles
                            obstacles[numObstacles] = new DestroyableObstacle(row, column, obstacleWidth, obstacleHeight);
                            obstacles[numObstacles].setRow(row);
                        }
                        //wenn aus 50 eine höhere zahl gemacht wird werden es weniger obstacles Felix trautmann
                        if(Math.random()*100 < 75){

                            obstacles[numObstacles].setInvisible();

                        }
                        else{
                            if(obstacles[numObstacles] instanceof DestroyableObstacle) destObstacles++;
                            else AnzObstacles++;
                        }

                        numObstacles ++;
                    }

                }
            }
            // minimum 5 undestroyable obstacles
            while(AnzObstacles<=3){
                int i =(int)((Math.random()*100)%numObstacles);
                if(!obstacles[i].getVisibility()  && !(obstacles[i] instanceof DestroyableObstacle)){
                    obstacles[i].setVisible();
                    AnzObstacles++;
                }
            }
            //max 6 undestroyable obstacles
            while(AnzObstacles>4){
                int i =(int)((Math.random()*100)%numObstacles);
                if(obstacles[i].getVisibility()  && !(obstacles[i] instanceof DestroyableObstacle)){
                    obstacles[i].setInvisible();
                    AnzObstacles--;
                }
            }

            // minimum 3 undestroyable obstacles
            while(destObstacles<=2){
                int i =(int)((Math.random()*100)%numObstacles);
                if(!obstacles[i].getVisibility()  ){
                    obstacles[i] = new DestroyableObstacle(obstacles[i].getRow(),obstacles[i].getColumn(),(int)obstacles[i].getWidth(),(int)obstacles[i].getHeight());
                    obstacles[i].setVisible();
                    destObstacles++;
                }
            }
            // max 3 undestroyable obstacles
            while(destObstacles>3){
                int i =(int)((Math.random()*100)%numObstacles);
                if(obstacles[i].getVisibility() && obstacles[i] instanceof DestroyableObstacle ){
                    obstacles[i].setInvisible();
                    destObstacles--;
                }
            }
        }//createnewObstacles

        @Override
        public void run() {
            while (playing) {

                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();

                // Update the frame
                // Update the frame
                if(!paused){
                    update();
                }

                // Draw the frame
                draw();

                // Calculate the fps this frame
                // We can then use the result to
                // time animations and more.
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1) {
                    fps = 1000 / timeThisFrame;
                }
            }
        }
        // Everything that needs to be updated goes in here
        // Movement, collision detection etc.
        public void update() {

            // Move the paddle if required
            paddle.update(fps);

            // Check for obstacle colliding
            for(int i = 0; i < numObstacles; i++){
                //with a ball

                    if (obstacles[i].getVisibility()){
                        obstacles[i].update(fps);
                        //if(RectF.intersects(obstacles[i].getRect(),ball.getRect())) {
                        boolean collidesFromBottom = ball.getRect().top<= obstacles[i].getRect().bottom && ball.getRect().bottom >= obstacles[i].getRect().bottom;
                        boolean collidesInX = ball.getRect().left <= obstacles[i].getRect().right && ball.getRect().right >=obstacles[i].getRect().left;
                        boolean collidesFromTop = ball.getRect().bottom>= obstacles[i].getRect().top && ball.getRect().top <= obstacles[i].getRect().bottom;
                        if( collidesInX &&(collidesFromBottom || collidesFromTop) ){

                            if(!boosted3) {
                                if(collidesFromBottom && ball.getyVelocity()<0)ball.clearObstacleY(obstacles[i].getRect().bottom + 24);
                                if(collidesFromTop&& ball.getyVelocity()>0)ball.clearObstacleY(obstacles[i].getRect().top);
                                soundPool.play(obstaclesound, 1, 1, 0, 0, 1);
                                ball.reverseYVelocity();
                            }
                                // create a new destroyable Obstacle if one is destroyed
                                if (obstacles[i] instanceof DestroyableObstacle) {
                                    obstacles[i].setInvisible();

                                    boolean weiter = true;
                                    while(weiter){
                                        int j =(int)((Math.random()*100)%numObstacles);
                                        boolean intersects = false;
                                        for (int k = 0; k < numObstacles; k++) {
                                            if (k == j) continue;
                                            if (RectF.intersects(obstacles[j].getRect(), obstacles[k].getRect()))
                                                intersects = true;
                                        }
                                        if (intersects) continue;
                                        obstacles[j] = new DestroyableObstacle(obstacles[j].getRow(),obstacles[j].getColumn(),(int)obstacles[j].getWidth(),(int)obstacles[j].getHeight());
                                        obstacles[j].setVisible();
                                        weiter = false;
                                    }

                                }
                        }
                    }//with a ball

                //with the wall
                if(obstacles[i].getRect().left <0 ) obstacles[i].setObstacleMoving(2);
                if(obstacles[i].getRect().right > screenX ) obstacles[i].setObstacleMoving(1);

                //with each other
                for(int j = 0; j <numObstacles; j++){
                    if (i!=j && obstacles[i].getRect().left <= obstacles[j].getRect().right && obstacles[i].getRect().right >= obstacles[j].getRect().right && obstacles[i].getRow() == obstacles[j].getRow()) {
                        if(obstacles[i].getVisibility() && obstacles[j].getVisibility()) {
                            obstacles[i].setObstacleMoving(2);
                            obstacles[j].setObstacleMoving(1);
                        }
                    }
                }
            }// for

            // Check for ball colliding with paddle
            if(RectF.intersects(paddle.getRect(),ball.getRect())) {

                //ball hits the paddle left or right and is not in alternateDirection Mode
                if((ball.getRect().centerX()<paddle.getRect().centerX()-50 || ball.getRect().centerX()>paddle.getRect().centerX()+50)&& ballAlternateDirection == false){
                    ball.setXVelocity((int)(ball.getxVelocity()*1.15));
                    ball.setYVelocity((int)(ball.getyVelocity()/1.15));
                    ballAlternateDirection = true;
                }
                // ball hits the paddle in the middle and is in alternateDirection Mode
                else if((ball.getRect().centerX()>paddle.getRect().centerX()-paddle.getlength()/4 || ball.getRect().centerX()<paddle.getRect().centerX()+paddle.getlength()/4)&&ballAlternateDirection){
                    ball.setXVelocity((int)(ball.getxVelocity()/1.15));
                    ball.setYVelocity((int)(ball.getyVelocity()*1.15));
                    ballAlternateDirection = false;
                }


                //wenn sich paddle nicht bewegt
                if (paddle.getpaddleMoving()==0){
                    ball.reverseYVelocity();
                    //ball.setRandomXVelocity();
                }
                //wenn sich paddle nach links bewegt
                if (paddle.getpaddleMoving()==1){
                    if(ball.xVelocity>0){
                        ball.reverseXVelocity();
                    }
                }
                //wenn sich paddle nach rechts bewegt
                if (paddle.getpaddleMoving()==2){
                    if(ball.xVelocity<0){
                        ball.reverseXVelocity();
                    }
                }


                if(ball.getyVelocity()>0){
                    ball.reverseYVelocity();
                    ball.clearObstacleY(paddle.getRect().top - 2);
                    soundPool.play(paddlesound, 1, 1, 0, 0, 1);
                }

            }// Collission Ball and Paddle

            // Check for ball colliding with coins
            for(int i=0;i<numcoins;i++) {
                if (RectF.intersects(ball.getRect(), coins[i].getRect())) {
                    coins[i].setInvisible();
                    // 2 facher Punktgewinn wenn boost 2
                    if(boosted2) score += 10;
                    // normaler Punktgewinn
                    else score += 5;
                    boolean repeat = true;

                    while(repeat){
                        repeat = false;
                        coins[i] = new Coin(screenX, screenY);
                        for(int j=0;j<numcoins;j++){
                            if(RectF.intersects(coins[i].getRect(),coins[j].getRect())&&j!=i){
                                repeat = true;
                            }
                        }
                    }



                    soundPool.play(geld, 1, 1, 0, 0, 1);
                }// Collission Ball and coin
            }

            // Check for ball colliding with boost
            if (RectF.intersects(ball.getRect(), boost.getRect())&&boost.getVisibility()==true) {
                boost.setInvisible();
                boostscore++;
                switch (boost.getTyp()){
                    case 1: if(boosted1) boostedtime1+=10;
                            boosted1 = true;
                            soundPool.play(flaschensound, 1, 1, 0, 0, 1);
                            break;
                    case 2: if(boosted2) boostedtime2+=10;
                            boosted2 = true;
                            soundPool.play(rechnen, 1, 1, 0, 0, 1);
                            break;
                    case 3: if(boosted3) boostedtime3+=10;
                            boosted3 = true;
                            soundPool.play(mouse, 1, 1, 0, 0, 1);
                            break;
                }
                noboost=5;
                boost = new Boost(screenX, screenY);

            }// Collission Ball and boost



            // Bounce the ball back when it hits the bottom of screen
            // And deduct a life
            if(ball.getRect().bottom > screenY){
                ball.reverseYVelocity();
                ball.clearObstacleY(screenY - 2);

                // Lose a life
                createnewObstacles();
                lives --;
                if(lives>0)soundPool.play(loseLifeID, 1, 1, 0, 0, 1);
                ball.makeballfaster();


                if(lives == 0){
                    paused = true;
                    //createObstaclesAndRestart();

                    SharedPreferences.Editor scoreEdit = gamePrefs.edit();

                    // If Score is not filled
                    if(!gamePrefs.contains("1")){
                        scoreEdit.putInt("1", score);
                        scoreEdit.putString("name1",gamePrefs.getString("name","name"));
                    }

                    else if (!gamePrefs.contains("2")) {
                        if (gamePrefs.getInt("1", 0) < score) {
                            scoreEdit.putInt("2", gamePrefs.getInt("1", 0));
                            scoreEdit.putString("name2",gamePrefs.getString("name1","name"));
                            scoreEdit.putInt("1", score);
                            scoreEdit.putString("name1",gamePrefs.getString("name","name"));
                        } else{
                            scoreEdit.putInt("2", score);
                            scoreEdit.putString("name2",gamePrefs.getString("name","name"));
                        }

                    }
                    else if (!gamePrefs.contains("3")){
                        if(gamePrefs.getInt("1",0)<score){
                            scoreEdit.putString("name3",gamePrefs.getString("name2","name"));
                            scoreEdit.putInt("3", gamePrefs.getInt("2",0));
                            scoreEdit.putString("name2",gamePrefs.getString("name1","name"));
                            scoreEdit.putInt("2", gamePrefs.getInt("1",0));
                            scoreEdit.putInt("1", score);
                            scoreEdit.putString("name1",gamePrefs.getString("name","name"));
                        }
                        else if(gamePrefs.getInt("2",0)<score){
                            scoreEdit.putString("name3",gamePrefs.getString("name2","name"));
                            scoreEdit.putInt("3", gamePrefs.getInt("2",0));
                            scoreEdit.putInt("2", score);
                            scoreEdit.putString("name2",gamePrefs.getString("name","name"));
                        }
                        else
                            scoreEdit.putInt("3", score);
                        scoreEdit.putString("name3",gamePrefs.getString("name","name"));
                    }

                    else if (!gamePrefs.contains("4")){
                        if(gamePrefs.getInt("1",0)<score){
                            scoreEdit.putString("name4",gamePrefs.getString("name3","name"));
                            scoreEdit.putInt("4", gamePrefs.getInt("3",0));
                            scoreEdit.putString("name3",gamePrefs.getString("name2","name"));
                            scoreEdit.putInt("3", gamePrefs.getInt("2",0));
                            scoreEdit.putString("name2",gamePrefs.getString("name1","name"));
                            scoreEdit.putInt("2", gamePrefs.getInt("1",0));
                            scoreEdit.putString("name1",gamePrefs.getString("name","name"));
                            scoreEdit.putInt("1", score);
                        }
                        else if(gamePrefs.getInt("2",0)<score){
                            scoreEdit.putString("name4",gamePrefs.getString("name3","name"));
                            scoreEdit.putInt("4", gamePrefs.getInt("3",0));
                            scoreEdit.putString("name3",gamePrefs.getString("name2","name"));
                            scoreEdit.putInt("3", gamePrefs.getInt("2",0));
                            scoreEdit.putString("name2",gamePrefs.getString("name","name"));
                            scoreEdit.putInt("2", score);
                        }
                        else if(gamePrefs.getInt("3",0)<score){
                            scoreEdit.putString("name4",gamePrefs.getString("name3","name"));
                            scoreEdit.putInt("4", gamePrefs.getInt("3",0));
                            scoreEdit.putString("name3",gamePrefs.getString("name","name"));
                            scoreEdit.putInt("3", score);
                        }
                        else{
                            scoreEdit.putInt("4", score);
                            scoreEdit.putString("name4",gamePrefs.getString("name","name"));
                        }

                    }
                    else if (!gamePrefs.contains("5")){
                        if(gamePrefs.getInt("1",0)<score){
                            scoreEdit.putString("name5",gamePrefs.getString("name4","name"));
                            scoreEdit.putString("name4",gamePrefs.getString("name3","name"));
                            scoreEdit.putString("name3",gamePrefs.getString("name2","name"));
                            scoreEdit.putString("name2",gamePrefs.getString("name1","name"));
                            scoreEdit.putString("name1",gamePrefs.getString("name","name"));
                            scoreEdit.putInt("5", gamePrefs.getInt("4",0));
                            scoreEdit.putInt("4", gamePrefs.getInt("3",0));
                            scoreEdit.putInt("3", gamePrefs.getInt("2",0));
                            scoreEdit.putInt("2", gamePrefs.getInt("1",0));
                            scoreEdit.putInt("1", score);
                        }
                        else if(gamePrefs.getInt("2",0)<score){
                            scoreEdit.putString("name5",gamePrefs.getString("name4","name"));
                            scoreEdit.putString("name4",gamePrefs.getString("name3","name"));
                            scoreEdit.putString("name3",gamePrefs.getString("name2","name"));
                            scoreEdit.putString("name2",gamePrefs.getString("name","name"));
                            scoreEdit.putInt("5", gamePrefs.getInt("4",0));
                            scoreEdit.putInt("4", gamePrefs.getInt("3",0));
                            scoreEdit.putInt("3", gamePrefs.getInt("2",0));
                            scoreEdit.putInt("2", score);
                        }
                        else if(gamePrefs.getInt("3",0)<score){
                            scoreEdit.putString("name5",gamePrefs.getString("name4","name"));
                            scoreEdit.putString("name4",gamePrefs.getString("name3","name"));
                            scoreEdit.putString("name3",gamePrefs.getString("name","name"));
                            scoreEdit.putInt("5", gamePrefs.getInt("4",0));
                            scoreEdit.putInt("4", gamePrefs.getInt("3",0));
                            scoreEdit.putInt("3", score);
                        }
                        else if(gamePrefs.getInt("4",0)<score){
                            scoreEdit.putString("name5",gamePrefs.getString("name4","name"));
                            scoreEdit.putString("name4",gamePrefs.getString("name","name"));
                            scoreEdit.putInt("5", gamePrefs.getInt("4",0));
                            scoreEdit.putInt("4", score);
                        }
                        else{
                            scoreEdit.putInt("5", score);
                            scoreEdit.putString("name5",gamePrefs.getString("name","name"));
                        }


                    }
                    // If ScoreList is filled
                    else {
                        if(gamePrefs.getInt("1",0)<score){
                            scoreEdit.putString("name5",gamePrefs.getString("name4","name"));
                            scoreEdit.putString("name4",gamePrefs.getString("name3","name"));
                            scoreEdit.putString("name3",gamePrefs.getString("name2","name"));
                            scoreEdit.putString("name2",gamePrefs.getString("name1","name"));
                            scoreEdit.putString("name1",gamePrefs.getString("name","name"));
                            scoreEdit.putInt("5", gamePrefs.getInt("4",0));
                            scoreEdit.putInt("4", gamePrefs.getInt("3",0));
                            scoreEdit.putInt("3", gamePrefs.getInt("2",0));
                            scoreEdit.putInt("2", gamePrefs.getInt("1",0));
                            scoreEdit.putInt("1", score);
                        }
                        else if(gamePrefs.getInt("2",0)<score){
                            scoreEdit.putString("name5",gamePrefs.getString("name4","name"));
                            scoreEdit.putString("name4",gamePrefs.getString("name3","name"));
                            scoreEdit.putString("name3",gamePrefs.getString("name2","name"));
                            scoreEdit.putString("name2",gamePrefs.getString("name","name"));
                            scoreEdit.putInt("5", gamePrefs.getInt("4",0));
                            scoreEdit.putInt("4", gamePrefs.getInt("3",0));
                            scoreEdit.putInt("3", gamePrefs.getInt("2",0));
                            scoreEdit.putInt("2", score);
                        }
                        else if(gamePrefs.getInt("3",0)<score){
                            scoreEdit.putString("name5",gamePrefs.getString("name4","name"));
                            scoreEdit.putString("name4",gamePrefs.getString("name3","name"));
                            scoreEdit.putString("name3",gamePrefs.getString("name","name"));
                            scoreEdit.putInt("5", gamePrefs.getInt("4",0));
                            scoreEdit.putInt("4", gamePrefs.getInt("3",0));
                            scoreEdit.putInt("3", score);
                        }
                        else if(gamePrefs.getInt("4",0)<score){
                            scoreEdit.putString("name5",gamePrefs.getString("name4","name"));
                            scoreEdit.putString("name4",gamePrefs.getString("name","name"));
                            scoreEdit.putInt("5", gamePrefs.getInt("4",0));
                            scoreEdit.putInt("4", score);
                        }
                        else if (gamePrefs.getInt("5",0)<score){
                            scoreEdit.putInt("5", score);
                            scoreEdit.putString("name5",gamePrefs.getString("name","name"));
                        }

                    }

                    scoreEdit.commit();
                    soundPool.play(gameoverdeath, 1, 1, 0, 0, 1);
                    startActivity(new Intent(BouncingGame.this, GameOverActivity.class));
                }

            }// Bounce ball from bottom

            // Bounce the ball back when it hits the top of screen
            if(ball.getRect().top < 0){
                ball.reverseYVelocity();
                ball.clearObstacleY(24);
                //soundPool.play(explodeID, 1, 1, 0, 0, 1);
            }// Bounce from Top

            // If the ball hits left wall bounce
            if(ball.getRect().left < 0){
                ball.reverseXVelocity();
                ball.clearObstacleX(4);
                //soundPool.play(explodeID, 1, 1, 0, 0, 1);
            }

            // If the ball hits right wall bounce
            if(ball.getRect().right > screenX - 20){
                ball.reverseXVelocity();
                ball.clearObstacleX(screenX - 44);
                //soundPool.play(explodeID, 1, 1, 0, 0, 1);
            }

            // Pause if cleared screen
            if(score == numObstacles * 10){
                paused = true;
                createObstaclesAndRestart();
            }

            // Update Ball
            ball.update(fps);

        }

        // Draw the newly updated scene
        public void draw() {

            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                canvas = ourHolder.lockCanvas();

                //ballpic und coin pic nach unten verlegt zu draw the ball

                background = createScaledBitmap(background,screenX,screenY,false);
                                                            // Hier die Werte aus Boost - Case 1 übernehmen
                boostpic1 = createScaledBitmap(boostpic1, 50, 190, false );
                                                            // Hier die Werte aus Boost - Case 2 übernehmen
                boostpic2 = createScaledBitmap(boostpic2, 140, 90, false );
                                                            // Hier die Werte aus Boost - Case 3 übernehmen
                boostpic3 = createScaledBitmap(boostpic3, 180, 100, false );
                obstaclepic = createScaledBitmap(obstaclepic, (int) (obstacles[0].getWidth() *0.9),(int) (obstacles[0].getHeight() *0.8),false);
                destroyableobstaclepic = createScaledBitmap(destroyableobstaclepic, (int) (obstacles[0].getWidth() *0.9),(int) (obstacles[0].getHeight() *0.8),false);


                // Draw the background
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(background, (0), (0), null);

                if(boosted1||boosted2||boosted3){
                    paint.setTextSize(80);
                    paint.setColor(Color.argb(255,  0, 0, 255));
                    canvas.drawText("Boosted", (screenX/2)-140,screenY/3, paint);
                }

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255,  255, 255, 255));
                //System.out.println(paddle.getPaddleSpeed()); == 350
                if (boosted1){ paddle.setPaddleSpeed(800);}
                else paddle.setPaddleSpeed(450);
                // Draw the paddle
                if(boosted1){
                    paddlepicblue = createScaledBitmap(paddlepicblue, (int)paddle.getlength(),(int) paddle.getheight(),false);
                    canvas.drawBitmap(paddlepicblue, paddle.getX(),paddle.getY(),null);
                }
                else{
                    paddlepic = createScaledBitmap(paddlepic, (int)paddle.getlength(),(int) paddle.getheight(),false);
                    canvas.drawBitmap(paddlepic, paddle.getX(),paddle.getY(),null);
                }

                //Counter für noboost // zählt jede sekunde
                if ((System.currentTimeMillis()-starttime)/1000>timer){
                    timer++;
                    timeranzeige--;
                    noboost--;
                    boost.setBoosttime(boost.getBoosttime()+1);
                    if(boosted1||boosted2||boosted3) {
                        if (boosted1) boostedtime1--;
                        if (boosted2) boostedtime2--;
                        if (boosted3) boostedtime3--;
                        if (boostedtime1<=0)if(boosted1){ boosted1 =false; boostedtime1 = 10;};
                        if (boostedtime2<=0)if(boosted2){ boosted2 =false; boostedtime2 = 10;}
                        if (boostedtime3<=0)if(boosted3){ boosted3 =false; boostedtime3 = 10;}
                    }
                    // Was nach xx(boostsichtbar) Sekunden passiert
                    if (boost.getBoosttime()>boostsichtbar){
                        boost.setInvisible();
                        noboost=5;
                        boost = new Boost(screenX, screenY);
                    }

                    //Zählt die sekunden und gibt pro sekunde +1 auf score
                    if (timer >=4) {
                        paused = false;
                        playtime++;
                        if (boosted2) score +=2;
                        else score++;
                    }
                }

                //Geschwindigkeit des Balles alle 20 Sekunden um 10 Prozent (Siehe Ball.makeballfaster())
                if (playtime == 20*difficulty){
                    ball.makeballfaster();
                    difficulty++;
                }
                // Draw the ball
                if(boosted3){
                    ballpicblue = createScaledBitmap(ballpicblue, (int) ball.ballWidth, (int) ball.ballHeight,false);
                    canvas.drawBitmap(ballpicblue, ball.rect.left,ball.rect.bottom,null);
                }
                else{
                    ballpic = createScaledBitmap(ballpic, (int) ball.ballWidth, (int) ball.ballHeight,false);
                    canvas.drawBitmap(ballpic, ball.rect.left,ball.rect.bottom,null);
                }

                // Draw the coin
                for (int i=0; i<numcoins; i++) {
                    if (coins[i].getVisibility()) {
                        if (boosted2){
                            coinpicblue = createScaledBitmap(coinpicblue, (int) coins[1].getLength(),(int) coins[1].getHeight(),false);
                            canvas.drawBitmap(coinpicblue, coins[i].getX(), coins[i].getY(), null);
                        }
                        else {
                            coinpic = createScaledBitmap(coinpic, (int) coins[1].getLength(),(int) coins[1].getHeight(),false);
                            canvas.drawBitmap(coinpic, coins[i].getX(), coins[i].getY(), null);
                        }
                    }
                }


                // Draw the Boost
                if (noboost<=0) boost.setVisible();
                if (boost.getVisibility()) {
                    switch (boost.typ){
                        case 1: canvas.drawBitmap(boostpic1, boost.getX(), boost.getY(), null);
                                break;
                                case 2: canvas.drawBitmap(boostpic2, boost.getX(), boost.getY(), null);
                                        break;
                                    case 3: canvas.drawBitmap(boostpic3, boost.getX(), boost.getY(), null);
                                            break;
                        }
                }



                // Change the obstacles color for drawing
                paint.setColor(Color.argb(255,  249, 129, 0));

                // Draw the obstacles if visible
                for(int i = 0; i < numObstacles; i++){
                    if(obstacles[i].getVisibility()) {

                        if(obstacles[i] instanceof DestroyableObstacle){
                            //paint.setColor(Color.argb(255,  0, 100, 255));
                            canvas.drawBitmap(destroyableobstaclepic, obstacles[i].getRect().left, obstacles[i].getRect().top, null);
                            //paint.setColor(Color.argb(255,  249, 129, 0));
                            continue;
                        }
                        else {
                            canvas.drawBitmap(obstaclepic, obstacles[i].getRect().left, obstacles[i].getRect().top, null);
                        }

                    }
                }

                // Draw the HUD
                // Choose the brush color for drawing
                paint.setColor(Color.argb(255,  255, 255, 255));

                // Draw the score, Lives, and Time played
                paint.setTextSize(40);

                //if(boosted2) paint.setColor(Color.argb(255,  0, 0, 255));
                canvas.drawText("Score: " + score + "   Lives: " + lives + "   Seconds Played: " + playtime + "  Gesammelte Boosts: "+boostscore, 10,50, paint);




                // Has the player cleared the screen?
                if(score == numObstacles * 10){
                    paint.setTextSize(90);
                    canvas.drawText("YOU HAVE WON!", 10,screenY/2, paint);
                }

                // Has the player lost?
                if(lives <= 0){
                    //do nothing
                }


                //Anzeigen des Countdowns
                if (timer<4) {
                    paint.setTextSize(90);
                    canvas.drawText("Spiel beginnt in "+timeranzeige+" Sekunden", screenX / 4, screenY / 2, paint);

                }
                if (timer==4){
                    paint.setTextSize(90);
                    canvas.drawText("Go", screenX / 2, screenY / 2, paint);
                }



                // Draw everything to the screen
                ourHolder.unlockCanvasAndPost(canvas);
            }


        }//draw

        // If SimpleGameEngine Activity is paused/stopped
        // shutdown our thread.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        // If SimpleGameEngine Activity is started theb
        // start our thread.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        // The SurfaceView class implements onTouchListener
        // So we can override this method and detect screen touches.
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            if(!touch){
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    // Player has touched the screen
                    case MotionEvent.ACTION_DOWN:

                        if(motionEvent.getX() > screenX / 2){
                            paddle.setMovementState(paddle.RIGHT);
                        }
                        else{
                            paddle.setMovementState(paddle.LEFT);
                        }

                        break;

                    // Player has removed finger from screen
                    case MotionEvent.ACTION_UP:

                        paddle.setMovementState(paddle.STOPPED);
                        break;
                }
            }
            return true;
        }





    }
    // This is the end of our BouncingView inner class

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

        // Tell the gameView resume method to execute
        bouncingView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);

        // Tell the gameView pause method to execute
        bouncingView.pause();
    }

}
// This is the end of the BouncingGame class