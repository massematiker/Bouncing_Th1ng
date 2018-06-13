package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class HelpActivity extends Activity {
    int count =0;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        configurestartgamebutton();
        configureGoPlayButton();

    }
    private void configureGoPlayButton(){


        final ImageButton startgameButton = (ImageButton) findViewById(R.id.startgame);
        final ImageButton newgameButton = (ImageButton) findViewById(R.id.goplay);
        newgameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0){
                    rl = (RelativeLayout) findViewById(R.id.RelativeLayoutHelp);
                    rl.setBackgroundResource(R.drawable.help2);
                    count++;
                }
                else if(count==1){
                    rl = (RelativeLayout) findViewById(R.id.RelativeLayoutHelp);
                    rl.setBackgroundResource(R.drawable.help3);
                    count++;
                }
                else if(count==2){
                    rl = (RelativeLayout) findViewById(R.id.RelativeLayoutHelp);
                    rl.setBackgroundResource(R.drawable.help4);

                    count++;
                }
                else if(count==3){
                    rl = (RelativeLayout) findViewById(R.id.RelativeLayoutHelp);
                    rl.setBackgroundResource(R.drawable.help5);
                    newgameButton.setBackgroundResource(R.drawable.play);
                    startgameButton.setVisibility(View.VISIBLE);
                    newgameButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private void configurestartgamebutton(){

        final ImageButton startgameButton = (ImageButton) findViewById(R.id.startgame);
        startgameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, BouncingGame.class));
            }
        });
    }


}

