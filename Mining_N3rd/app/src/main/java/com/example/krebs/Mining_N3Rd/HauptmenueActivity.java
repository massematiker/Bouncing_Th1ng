package com.example.krebs.Mining_N3Rd;

import android.app.Activity;
import android.arch.lifecycle.LifecycleObserver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.krebs.Mining_N3Rd.R;

public class HauptmenueActivity extends Activity implements LifecycleObserver {
    private String name;
    private EditText nameInput;
    private boolean playTouch;
    private boolean helpMenu;
    private boolean backgroundMusic;
    private CheckBox checkBoxTouch;
    private CheckBox checkBoxHelp;
    private CheckBox checkBoxBackgroundMusic;

    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";
    private boolean stillStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        stillStarted = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenue);
        configureNewGameButtonMain();
        ConfigureHighScoreButton();

        gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        SharedPreferences.Editor nameEdit = gamePrefs.edit();
        nameInput = (EditText) findViewById(R.id.nameinput);
        nameInput.setText(gamePrefs.getString("name","name"));


    }

    @Override
    public void onResume(){
        super.onResume();
        stillStarted = false;

    }


    /**
     * here is configured what happens if the NewGameButton is pressed
     */
    private void configureNewGameButtonMain(){

        ImageButton newgameButton = (ImageButton) findViewById(R.id.newgamebuttonhaupt);

            newgameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stillStarted == false) {
                        //writes the checkboxes to a boolean
                        checkBoxTouch = (CheckBox) findViewById(R.id.cbTouch);
                        playTouch = checkBoxTouch.isChecked();
                        checkBoxHelp = (CheckBox) findViewById(R.id.cbHilfeScreen);
                        checkBoxBackgroundMusic = (CheckBox) findViewById(R.id.cbBackgroundMusic);
                        helpMenu = checkBoxHelp.isChecked();
                        backgroundMusic = checkBoxBackgroundMusic.isChecked();

                        nameInput = (EditText) findViewById(R.id.nameinput);
                        name = nameInput.getText().toString();


                        // get The Name and save
                        gamePrefs = getSharedPreferences(GAME_PREFS, 0);
                        SharedPreferences.Editor nameEdit = gamePrefs.edit();

                        nameEdit.putString("name", name);
                        nameEdit.putBoolean("touch", playTouch);
                        nameEdit.putBoolean("backgroundMusic", backgroundMusic);
                        nameEdit.commit();


                        if (helpMenu)
                            startActivity(new Intent(HauptmenueActivity.this, HelpActivity.class));
                        else startActivity(new Intent(HauptmenueActivity.this, BouncingGame.class));
                    }
                    stillStarted = true;
                }
            });



    }
    /**
     * here is configured what happens if the HighScoreButton is pressed
     */
    private void  ConfigureHighScoreButton(){
        ImageButton highscorebutton = (ImageButton ) findViewById(R.id.highscorebutton);
        highscorebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HauptmenueActivity.this, HighscoreActivity.class));
            }
        });
    }

}