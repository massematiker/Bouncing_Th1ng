package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class HighscoreActivity extends Activity {

    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        configuremenubutton1();

        TextView HighScore1 = (TextView) findViewById(R.id.highScore1);
        TextView HighScore2 = (TextView) findViewById(R.id.highScore2);
        TextView HighScore3 = (TextView) findViewById(R.id.highScore3);
        TextView HighScore4 = (TextView) findViewById(R.id.highScore4);
        TextView HighScore5 = (TextView) findViewById(R.id.highScore5);


        gamePrefs = getSharedPreferences(GAME_PREFS, 0);

        if(!gamePrefs.contains("1")) {
            HighScore1.setText("1   : 0");
            HighScore2.setText("2   : 0");
            HighScore3.setText("3   : 0");
            HighScore4.setText("4   : 0");
            HighScore5.setText("5   : 0");
        }
        else if(!gamePrefs.contains("2")){
            HighScore1.setText("1  "+gamePrefs.getString("name1","name")+" : "+ gamePrefs.getInt("1",0));
            HighScore2.setText("2   : 0");
            HighScore3.setText("3   : 0");
            HighScore4.setText("4   : 0");
            HighScore5.setText("5   : 0");
        }
        else if(!gamePrefs.contains("3")){
            HighScore1.setText("1  "+gamePrefs.getString("name1","name")+" : "+ gamePrefs.getInt("1",0));
            HighScore2.setText("2  "+gamePrefs.getString("name2","name")+" : "+ gamePrefs.getInt("2",0));
            HighScore3.setText("3   : 0");
            HighScore4.setText("4   : 0");
            HighScore5.setText("5   : 0");
        }
        else if(!gamePrefs.contains("4")){
            HighScore1.setText("1  "+gamePrefs.getString("name1","name")+" : "+ gamePrefs.getInt("1",0));
            HighScore2.setText("2  "+gamePrefs.getString("name2","name")+" : "+ gamePrefs.getInt("2",0));
            HighScore3.setText("3  "+gamePrefs.getString("name3","name")+" : "+ gamePrefs.getInt("3",0));
            HighScore4.setText("4   : 0");
            HighScore5.setText("5   : 0");
        }
        else if(!gamePrefs.contains("5")){
            HighScore1.setText("1  "+gamePrefs.getString("name1","name")+" : "+ gamePrefs.getInt("1",0));
            HighScore2.setText("2  "+gamePrefs.getString("name2","name")+" : "+ gamePrefs.getInt("2",0));
            HighScore3.setText("3  "+gamePrefs.getString("name3","name")+" : "+ gamePrefs.getInt("3",0));
            HighScore4.setText("4  "+gamePrefs.getString("name4","name")+" : "+ gamePrefs.getInt("4",0));
            HighScore5.setText("5   : 0");

        }
        else{
            HighScore1.setText("1  "+gamePrefs.getString("name1","name")+" : "+ gamePrefs.getInt("1",0));
            HighScore2.setText("2  "+gamePrefs.getString("name2","name")+" : "+ gamePrefs.getInt("2",0));
            HighScore3.setText("3  "+gamePrefs.getString("name3","name")+" : "+ gamePrefs.getInt("3",0));
            HighScore4.setText("4  "+gamePrefs.getString("name4","name")+" : "+ gamePrefs.getInt("4",0));
            HighScore5.setText("5  "+gamePrefs.getString("name5","name")+" : "+ gamePrefs.getInt("5",0));
        }





    }
    private void configuremenubutton1(){
        ImageButton menuButton1 = (ImageButton) findViewById(R.id.menubutton1);
        menuButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HighscoreActivity.this, HauptmenueActivity.class));
            }
        });
    }
}
