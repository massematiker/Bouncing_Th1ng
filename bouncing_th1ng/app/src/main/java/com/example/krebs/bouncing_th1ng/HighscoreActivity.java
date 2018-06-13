package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class HighscoreActivity extends Activity {

    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        configureMenuButton1();

        TextView highScore1 = (TextView) findViewById(R.id.highScore1);
        TextView highScore2 = (TextView) findViewById(R.id.highScore2);
        TextView highScore3 = (TextView) findViewById(R.id.highScore3);
        TextView highScore4 = (TextView) findViewById(R.id.highScore4);
        TextView highScore5 = (TextView) findViewById(R.id.highScore5);
        TextView score1 = (TextView) findViewById(R.id.Score1);
        TextView score2 = (TextView) findViewById(R.id.Score2);
        TextView score3 = (TextView) findViewById(R.id.Score3);
        TextView score4 = (TextView) findViewById(R.id.Score4);
        TextView score5 = (TextView) findViewById(R.id.Score5);

        // get the Saved HighScore and Names
        gamePrefs = getSharedPreferences(GAME_PREFS, 0);

        // If the HighScore List is empty
        if(!gamePrefs.contains("1")) {
            highScore1.setText("");
            highScore2.setText("");
            highScore3.setText("");
            highScore4.setText("");
            highScore5.setText("");
            score1.setText("");
            score2.setText("");
            score3.setText("");
            score4.setText("");
            score5.setText("");
        }
        // If the HighScoreL List only contains 1 Item
        else if(!gamePrefs.contains("2")){
            highScore1.setText(gamePrefs.getString("name1","name"));
            highScore2.setText("");
            highScore3.setText("");
            highScore4.setText("");
            highScore5.setText("");
            score1.setText(Integer.toString(gamePrefs.getInt("1",0)));
            score2.setText("");
            score3.setText("");
            score4.setText("");
            score5.setText("");
        }
        // If the HighScoreL List only contains 2 Item
        else if(!gamePrefs.contains("3")){
            highScore1.setText(gamePrefs.getString("name1","name"));
            highScore2.setText(gamePrefs.getString("name2","name"));
            highScore3.setText("");
            highScore4.setText("");
            highScore5.setText("");
            score1.setText(Integer.toString(gamePrefs.getInt("1",0)));
            score2.setText(Integer.toString(gamePrefs.getInt("2",0)));
            score3.setText("");
            score4.setText("");
            score5.setText("");
        }
        // If the HighScoreL List only contains 3 Item
        else if(!gamePrefs.contains("4")){
            highScore1.setText(gamePrefs.getString("name1","name"));
            highScore2.setText(gamePrefs.getString("name2","name"));
            highScore3.setText(gamePrefs.getString("name3","name"));
            highScore4.setText("");
            highScore5.setText("");
            score1.setText(Integer.toString(gamePrefs.getInt("1",0)));
            score2.setText(Integer.toString(gamePrefs.getInt("2",0)));
            score3.setText(Integer.toString(gamePrefs.getInt("3",0)));
            score4.setText("");
            score5.setText("");
        }
        // If the HighScoreL List only contains 4 Item
        else if(!gamePrefs.contains("5")){
            highScore1.setText(gamePrefs.getString("name1","name"));
            highScore2.setText(gamePrefs.getString("name2","name"));
            highScore3.setText(gamePrefs.getString("name3","name"));
            highScore4.setText(gamePrefs.getString("name4","name"));
            highScore5.setText("");
            score1.setText(Integer.toString(gamePrefs.getInt("1",0)));
            score2.setText(Integer.toString(gamePrefs.getInt("2",0)));
            score3.setText(Integer.toString(gamePrefs.getInt("3",0)));
            score4.setText(Integer.toString(gamePrefs.getInt("4",0)));
            score5.setText("");

        }
        // If 5 HighScores are available
        else{
            highScore1.setText(gamePrefs.getString("name1","name"));
            highScore2.setText(gamePrefs.getString("name2","name"));
            highScore3.setText(gamePrefs.getString("name3","name"));
            highScore4.setText(gamePrefs.getString("name4","name"));
            highScore5.setText(gamePrefs.getString("name5","name"));
            score1.setText(Integer.toString(gamePrefs.getInt("1",0)));
            score2.setText(Integer.toString(gamePrefs.getInt("2",0)));
            score3.setText(Integer.toString(gamePrefs.getInt("3",0)));
            score4.setText(Integer.toString(gamePrefs.getInt("4",0)));
            score5.setText(Integer.toString(gamePrefs.getInt("5",0)));
        }





    }

    /**
     * what happens if the MenueButton is pressed
     */
    private void configureMenuButton1(){
        ImageButton menuButton1 = (ImageButton) findViewById(R.id.menubutton1);
        menuButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HighscoreActivity.this, HauptmenueActivity.class));
            }
        });
    }
}