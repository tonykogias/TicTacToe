package com.example.tonykogias.tictactoe;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Button exit_butt;
    Button butt_33;
    Button butt_44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.rsz_taskbaricon);
        }

        //3x3 BUTTON
        butt_33 = (Button) findViewById(R.id.button_3x3);
        butt_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChooseOpp.class);
                intent.putExtra("buttonID",butt_33.getId());
                startActivity(intent);
            }
        });

        //4x4 BUTTON
        butt_44 = (Button) findViewById(R.id.button_4x4);
        butt_44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChooseOpp.class);
                intent.putExtra("buttonID",butt_44.getId());
                startActivity(intent);
            }
        });

        //EXIT BUTTON
        exit_butt = (Button) findViewById(R.id.button_exit);
        exit_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

}
