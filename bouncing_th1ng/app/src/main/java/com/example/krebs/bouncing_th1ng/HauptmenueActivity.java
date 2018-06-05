package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HauptmenueActivity extends Activity {
    String name;
    EditText nameinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenue);
        configurenewgamebuttonhaupt();


    }
    private void configurenewgamebuttonhaupt(){
        Button newgameButton = (Button) findViewById(R.id.newgamebuttonhaupt);
        newgameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameinput = (EditText) findViewById(R.id.nameinput);
                name = nameinput.getText().toString();
                startActivity(new Intent(HauptmenueActivity.this, BouncingGame.class));
            }
        });
    }
}
