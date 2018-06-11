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
        TextView Score1 = (TextView) findViewById(R.id.Score1);
        TextView Score2 = (TextView) findViewById(R.id.Score2);
        TextView Score3 = (TextView) findViewById(R.id.Score3);
        TextView Score4 = (TextView) findViewById(R.id.Score4);
        TextView Score5 = (TextView) findViewById(R.id.Score5);


        gamePrefs = getSharedPreferences(GAME_PREFS, 0);

        if(!gamePrefs.contains("1")) {
            HighScore1.setText("");
            HighScore2.setText("");
            HighScore3.setText("");
            HighScore4.setText("");
            HighScore5.setText("");
            Score1.setText("");
            Score2.setText("");
            Score3.setText("");
            Score4.setText("");
            Score5.setText("");
        }
        else if(!gamePrefs.contains("2")){
            HighScore1.setText(gamePrefs.getString("name1","name"));
            HighScore2.setText("");
            HighScore3.setText("");
            HighScore4.setText("");
            HighScore5.setText("");
            Score1.setText(Integer.toString(gamePrefs.getInt("1",0)));
            Score2.setText("");
            Score3.setText("");
            Score4.setText("");
            Score5.setText("");
        }
        else if(!gamePrefs.contains("3")){
            HighScore1.setText(gamePrefs.getString("name1","name"));
            HighScore2.setText(gamePrefs.getString("name2","name"));
            HighScore3.setText("");
            HighScore4.setText("");
            HighScore5.setText("");
            Score1.setText(Integer.toString(gamePrefs.getInt("1",0)));
            Score2.setText(Integer.toString(gamePrefs.getInt("2",0)));
            Score3.setText("");
            Score4.setText("");
            Score5.setText("");
        }
        else if(!gamePrefs.contains("4")){
            HighScore1.setText(gamePrefs.getString("name1","name"));
            HighScore2.setText(gamePrefs.getString("name2","name"));
            HighScore3.setText(gamePrefs.getString("name3","name"));
            HighScore4.setText("");
            HighScore5.setText("");
            Score1.setText(Integer.toString(gamePrefs.getInt("1",0)));
            Score2.setText(Integer.toString(gamePrefs.getInt("2",0)));
            Score3.setText(Integer.toString(gamePrefs.getInt("3",0)));
            Score4.setText("");
            Score5.setText("");
        }
        else if(!gamePrefs.contains("5")){
            HighScore1.setText(gamePrefs.getString("name1","name"));
            HighScore2.setText(gamePrefs.getString("name2","name"));
            HighScore3.setText(gamePrefs.getString("name3","name"));
            HighScore4.setText(gamePrefs.getString("name4","name"));
            HighScore5.setText("");
            Score1.setText(Integer.toString(gamePrefs.getInt("1",0)));
            Score2.setText(Integer.toString(gamePrefs.getInt("2",0)));
            Score3.setText(Integer.toString(gamePrefs.getInt("3",0)));
            Score4.setText(Integer.toString(gamePrefs.getInt("4",0)));
            Score5.setText("");

        }
        else{
            HighScore1.setText(gamePrefs.getString("name1","name"));
            HighScore2.setText(gamePrefs.getString("name2","name"));
            HighScore3.setText(gamePrefs.getString("name3","name"));
            HighScore4.setText(gamePrefs.getString("name4","name"));
            HighScore5.setText(gamePrefs.getString("name5","name"));
            Score1.setText(Integer.toString(gamePrefs.getInt("1",0)));
            Score2.setText(Integer.toString(gamePrefs.getInt("2",0)));
            Score3.setText(Integer.toString(gamePrefs.getInt("3",0)));
            Score4.setText(Integer.toString(gamePrefs.getInt("4",0)));
            Score5.setText(Integer.toString(gamePrefs.getInt("5",0)));
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
