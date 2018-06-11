package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HighscoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        configuremenubutton1();
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
